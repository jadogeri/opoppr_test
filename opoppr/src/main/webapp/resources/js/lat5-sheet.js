/* Context menus, numeric editing, and required-field highlighting for LAT5 sheets. */
(function (window) {
    "use strict";

    var REQUIRED_CELL_CLASS = "lat5-required-cell";
    var REQUIRED_ROW_CLASS = "lat5-required-row";

    function isEmpty(value) {
        return value === null || value === undefined || String(value).trim() === "";
    }

    function selectionBounds(sheet) {
        var selected = sheet.getSelected && sheet.getSelected();
        if (!selected || !selected.length) {
            return null;
        }

        var bounds = { startRow: Infinity, endRow: -Infinity, startCol: Infinity, endCol: -Infinity };
        selected.forEach(function (range) {
            bounds.startRow = Math.min(bounds.startRow, Math.min(range[0], range[2]));
            bounds.endRow = Math.max(bounds.endRow, Math.max(range[0], range[2]));
            bounds.startCol = Math.min(bounds.startCol, Math.min(range[1], range[3]));
            bounds.endCol = Math.max(bounds.endCol, Math.max(range[1], range[3]));
        });
        return bounds.startRow === Infinity ? null : bounds;
    }

    function hasSelection() {
        return !selectionBounds(this);
    }

    function editCell() {
        var range = selectionBounds(this);
        if (!range) {
            return;
        }
        this.selectCell(range.startRow, range.startCol);
        this.beginEditing();
    }

    function clearSelectedContent() {
        if (typeof this.emptySelectedCells === "function") {
            this.emptySelectedCells();
        } else if (typeof this.clear === "function") {
            this.clear();
        }
    }

    function addNewRow() {
        var form = window.jQuery(this.rootElement).closest("form");
        var button = form.find(".sheet-add-row:visible").first();
        if (button.length) {
            button.trigger("click");
        }
    }

    function deleteSelectedRows(deleteValue, clearEntireRow, columnCount) {
        var bounds = selectionBounds(this);
        if (!bounds) {
            return;
        }

        var changes = [];
        for (var row = bounds.startRow; row <= bounds.endRow; row++) {
            if (clearEntireRow) {
                for (var col = 0; col < columnCount; col++) {
                    changes.push([row, col, ""]);
                }
            } else {
                // Sections 2-4 use the server-side property selector to mark deletion.
                changes.push([row, 0, deleteValue]);
            }
        }
        this.setDataAtCell(changes, "contextMenu.deleteRow");
    }

    function commonItems(options) {
        var items = {
            edit_cell: {
                name: "Edit Cell",
                disabled: hasSelection,
                callback: editCell
            }
        };

        if (options.includeRowActions) {
            items.add_row = {
                name: "Add New Row",
                callback: addNewRow
            };
            items.delete_row = {
                name: "Delete Selected Row(s)",
                disabled: hasSelection,
                callback: function () {
                    deleteSelectedRows.call(this, options.deleteValue, options.clearEntireRow, options.columnCount);
                }
            };
        }

        items.hsep1 = "---------";
        items.undo = { name: "Undo" };
        items.redo = { name: "Redo" };
        items.hsep2 = "---------";
        items.clear_custom = {
            name: "Clear Content Only",
            disabled: hasSelection,
            callback: clearSelectedContent
        };
        items.alignment = { name: "Alignment" };
        items.make_read_only = { name: "Read only" };
        return items;
    }

    function markInvalid(state, row, col) {
        state.invalidCells[row + ":" + col] = true;
        state.invalidRows[row] = true;
    }

    function validateConditionalRow(row, state, mode) {
        if (mode === "none") {
            return;
        }
        var active = row.some(function (value) { return !isEmpty(value); });
        if (!active) {
            return;
        }

        if (isEmpty(row[0])) {
            markInvalid(state, state.row, 0);
            return;
        }

        if (mode === "consigned") {
            for (var c = 1; c < 6; c++) {
                if (isEmpty(row[c])) {
                    markInvalid(state, state.row, c);
                }
            }
            return;
        }

        if (mode === "other" && String(row[0]).trim().toLowerCase() === "other miscellaneous property" && isEmpty(row[1])) {
            markInvalid(state, state.row, 1);
        }
        if (isEmpty(row[mode === "other" ? 2 : 1])) {
            markInvalid(state, state.row, mode === "other" ? 2 : 1);
        }
        if (isEmpty(row[mode === "other" ? 3 : 2])) {
            markInvalid(state, state.row, mode === "other" ? 3 : 2);
        }
    }

    function refreshRequiredState(sheet, state, mode) {
        state.invalidCells = {};
        state.invalidRows = {};
        var data = sheet.getData ? sheet.getData() : [];
        data.forEach(function (row, rowIndex) {
            state.row = rowIndex;
            validateConditionalRow(row, state, mode);
        });
        delete state.row;
    }

    function toggleClass(element, className, enabled) {
        if (!element || !element.classList) {
            return;
        }
        if (enabled) {
            element.classList.add(className);
        } else {
            element.classList.remove(className);
        }
    }

    function sanitizeNumericChanges(changes, numericColumns) {
        if (!changes) {
            return;
        }
        changes.forEach(function (change) {
            var column = Number(change[1]);
            if (numericColumns.indexOf(column) === -1 || change[3] === null || change[3] === undefined) {
                return;
            }
            if (typeof change[3] === "string") {
                var raw = change[3].trim();
                if (raw === "") {
                    change[3] = null;
                    return;
                }
                var cleaned = raw.replace(/,/g, "").replace(/[^0-9.-]/g, "");
                var numeric = Number(cleaned);
                change[3] = Number.isFinite(numeric) ? numeric : null;
            }
        });
    }

    function configureSheet(options) {
        var state = { invalidCells: {}, invalidRows: {} };
        var originalBeforeChange = this.cfg.beforeChange;
        var originalAfterChange = this.cfg.afterChange;
        var originalAfterRenderer = this.cfg.afterRenderer;

        this.cfg.contextMenu = { items: commonItems(options) };
        this.cfg.beforeChange = function (changes, source) {
            sanitizeNumericChanges(changes, options.numericColumns);
            if (typeof originalBeforeChange === "function") {
                originalBeforeChange.apply(this, arguments);
            }
        };
        this.cfg.afterChange = function (changes, source) {
            if (source !== "loadData") {
                refreshRequiredState(this, state, options.validationMode);
            }
            if (typeof originalAfterChange === "function") {
                originalAfterChange.apply(this, arguments);
            }
        };
        this.cfg.afterInit = function () {
            refreshRequiredState(this, state, options.validationMode);
        };
        this.cfg.afterRenderer = function (TD, row, col) {
            var invalid = !!state.invalidCells[row + ":" + col];
            toggleClass(TD, REQUIRED_CELL_CLASS, invalid);
            toggleClass(TD.parentNode, REQUIRED_ROW_CLASS, !!state.invalidRows[row]);
            if (typeof originalAfterRenderer === "function") {
                originalAfterRenderer.apply(this, arguments);
            }
        };
    }

    window.inventorySheetExtender = function () {
        configureSheet.call(this, {
            includeRowActions: false,
            deleteValue: "",
            clearEntireRow: false,
            columnCount: 8,
            numericColumns: [1, 2, 3, 4, 5, 6],
            validationMode: "none"
        });
    };

    window.filingSheetExtender = function () {
        configureSheet.call(this, {
            includeRowActions: true,
            deleteValue: "Delete Row",
            clearEntireRow: false,
            columnCount: 3,
            numericColumns: [1, 2],
            validationMode: "standard"
        });
    };

    window.otherFilingSheetExtender = function () {
        configureSheet.call(this, {
            includeRowActions: true,
            deleteValue: "Delete Row",
            clearEntireRow: false,
            columnCount: 4,
            numericColumns: [2, 3],
            validationMode: "other"
        });
    };

    window.section5SheetExtender = function () {
        configureSheet.call(this, {
            includeRowActions: true,
            deleteValue: "",
            clearEntireRow: true,
            columnCount: 6,
            numericColumns: [4, 5],
            validationMode: "consigned"
        });
    };

    window.sheetExtender = window.filingSheetExtender;
}(window));
