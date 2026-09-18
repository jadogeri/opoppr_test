/**
 * Shared LAT5 PrimeFaces Extensions Sheet behavior.
 *
 * Each public extender below supplies the sheet-specific column count, numeric
 * columns, deletion convention, and validation mode. The common callbacks keep
 * validation state in the browser, so selecting a property does not immediately
 * paint its dependent empty cells red. The state activates when Next/Previous is
 * pressed or when the server returns an invalid row, and then each remaining
 * invalid cell is red while its row remains highlighted until every error in that
 * row is fixed.
 */
(function (window) {
    "use strict";

    var REQUIRED_CELL_CLASS = "lat5-required-cell";
    var REQUIRED_ROW_CLASS = "lat5-required-row";
    var registry = window.__lat5SheetRegistry = window.__lat5SheetRegistry || {};

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
        bounds.startRow = Math.max(0, bounds.startRow);
        bounds.endRow = Math.max(-1, bounds.endRow);
        return bounds.startRow === Infinity ? null : bounds;
    }

    function hasSelection() {
        return !selectionBounds(this);
    }

    function editCell() {
        var range = selectionBounds(this);
        if (!range) return;
        this.selectCell(range.startRow, range.startCol);
        this.beginEditing();
    }

    function clearSelectedContent() {
        if (typeof this.emptySelectedCells === "function") this.emptySelectedCells();
        else if (typeof this.clear === "function") this.clear();
    }

    function formFor(sheet) {
        return window.jQuery(sheet.rootElement).closest("form");
    }

    function addNewRow() {
        var button = formFor(this).find(".sheet-add-row:visible").first();
        if (button.length) button.trigger("click");
    }

    function deleteSelectedRows(deleteValue, clearEntireRow, columnCount) {
        var bounds = selectionBounds(this);
        if (!bounds) return;
        var changes = [];
        for (var row = bounds.startRow; row <= bounds.endRow; row++) {
            if (clearEntireRow) {
                for (var col = 0; col < columnCount; col++) changes.push([row, col, ""]);
            } else {
                changes.push([row, 0, deleteValue]);
            }
        }
        this.setDataAtCell(changes, "contextMenu.deleteRow");
    }

    function numericValueIsValid(value, range) {
        if (isEmpty(value)) return false;
        if (!range) return true;
        var number = Number(value);
        return Number.isFinite(number)
            && (range.min === undefined || number >= range.min)
            && (range.max === undefined || number <= range.max);
    }

    function rowIsComplete(row, mode, numericRanges) {
        if (mode === "none") return true;
        if (!row || !row.length) return false;
        if (mode === "consigned") {
            for (var c = 0; c < 6; c++) if (isEmpty(row[c])) return false;
            return true;
        }
        if (isEmpty(row[0])) return false;
        if (mode === "other" && String(row[0]).trim().toLowerCase() === "other miscellaneous property" && isEmpty(row[1])) return false;
        var yearColumn = mode === "other" ? 2 : 1;
        var costColumn = mode === "other" ? 3 : 2;
        return numericValueIsValid(row[yearColumn], numericRanges && numericRanges[yearColumn])
            && numericValueIsValid(row[costColumn], numericRanges && numericRanges[costColumn]);
    }

    function insertionDisabled() {
        var bounds = selectionBounds(this);
        if (!bounds) return true;
        var data = this.getData ? this.getData() : [];
        for (var row = bounds.startRow; row <= bounds.endRow; row++) {
            if (!rowIsComplete(data[row], this.cfg.lat5ValidationMode, this.cfg.lat5ValidationRanges)) return true;
        }
        return false;
    }

    function requestInsertedRow(position) {
        var bounds = selectionBounds(this);
        if (!bounds || insertionDisabled.call(this)) return;
        var index = position === "above" ? bounds.startRow : bounds.endRow + 1;
        var form = formFor(this);
        var input = form.find(".sheet-insert-row-index").first();
        var button = form.find(".sheet-insert-row-at").first();
        if (!input.length || !button.length) return;
        input.val(index);
        button.trigger("click");
    }

    function commonItems(options) {
        var items = {
            edit_cell: { name: "Edit Cell", disabled: hasSelection, callback: editCell }
        };
        if (options.includeRowActions) {
            items.add_row_above = { name: "Add Row Above", disabled: insertionDisabled, callback: function () { requestInsertedRow.call(this, "above"); } };
            items.add_row_below = { name: "Add Row Below", disabled: insertionDisabled, callback: function () { requestInsertedRow.call(this, "below"); } };
            items.add_row = { name: "Add New Row", callback: addNewRow };
            items.delete_row = {
                name: "Delete Selected Row(s)", disabled: hasSelection,
                callback: function () { deleteSelectedRows.call(this, options.deleteValue, options.clearEntireRow, options.columnCount); }
            };
        }
        items.hsep1 = "---------";
        items.undo = { name: "Undo" };
        items.redo = { name: "Redo" };
        items.hsep2 = "---------";
        items.clear_custom = { name: "Clear Content Only", disabled: hasSelection, callback: clearSelectedContent };
        items.alignment = { name: "Alignment" };
        items.make_read_only = { name: "Read only" };
        return items;
    }

    function markInvalid(state, row, col) {
        state.invalidCells[row + ":" + col] = true;
        state.invalidRows[row] = true;
    }

    function validateConditionalRow(row, state, mode, numericRanges) {
        if (mode === "none") return;
        var active = row && row.some(function (value) { return !isEmpty(value); });
        if (!active) return;
        if (mode === "consigned") {
            for (var c = 0; c < 6; c++) if (isEmpty(row[c])) markInvalid(state, state.row, c);
            return;
        }
        if (isEmpty(row[0])) {
            markInvalid(state, state.row, 0);
            return;
        }
        if (mode === "other" && String(row[0]).trim().toLowerCase() === "other miscellaneous property" && isEmpty(row[1])) {
            markInvalid(state, state.row, 1);
        }
        var yearColumn = mode === "other" ? 2 : 1;
        var costColumn = mode === "other" ? 3 : 2;
        if (!numericValueIsValid(row[yearColumn], numericRanges && numericRanges[yearColumn])) markInvalid(state, state.row, yearColumn);
        if (!numericValueIsValid(row[costColumn], numericRanges && numericRanges[costColumn])) markInvalid(state, state.row, costColumn);
    }

    function refreshRequiredState(sheet, state, mode, numericRanges) {
        state.invalidCells = {};
        state.invalidRows = {};
        if (!state.validationActive) return;
        var data = sheet.getData ? sheet.getData() : [];
        data.forEach(function (row, rowIndex) {
            state.row = rowIndex;
            validateConditionalRow(row, state, mode, numericRanges);
        });
        delete state.row;
    }

    function toggleClass(element, className, enabled) {
        if (!element || !element.classList) return;
        if (enabled) element.classList.add(className);
        else element.classList.remove(className);
    }

    function hasServerErrorMarker(sheet) {
        return window.jQuery(sheet.rootElement).find("tr.error, tr.grid-error, td.cell-empty-error, td.htInvalid").length > 0;
    }

    function activateAllSheets() {
        Object.keys(registry).forEach(function (key) {
            var entry = registry[key];
            if (!entry || !entry.sheet || !entry.sheet.ht) return;
            entry.state.validationActive = true;
            refreshRequiredState(entry.sheet, entry.state, entry.options.validationMode, entry.options.numericRanges);
            entry.sheet.render();
        });
    }

    function installNavigationHook() {
        if (window.__lat5NavigationHookInstalled || !window.jQuery) return;
        window.__lat5NavigationHookInstalled = true;
        window.jQuery(document).on("click.lat5Validation", ".saveForm .submitForm", activateAllSheets);
    }

    function sanitizeNumericChanges(changes, numericColumns) {
        if (!changes) return;
        changes.forEach(function (change) {
            var column = Number(change[1]);
            if (numericColumns.indexOf(column) === -1 || change[3] === null || change[3] === undefined) return;
            if (typeof change[3] === "string") {
                var raw = change[3].trim();
                if (raw === "") { change[3] = null; return; }
                var numeric = Number(raw.replace(/,/g, "").replace(/[^0-9.-]/g, ""));
                change[3] = Number.isFinite(numeric) ? numeric : null;
            }
        });
    }

    /**
     * Configures one Sheet instance.
     * @param {Object} options behavior contract for the section
     * @param {boolean} options.includeRowActions enables persisted row actions
     * @param {string} options.deleteValue selector value used by Sections 2-4
     * @param {boolean} options.clearEntireRow clears every cell in Section 5
     * @param {number} options.columnCount physical cell count used for deletion
     * @param {number[]} options.numericColumns zero-based numeric columns to sanitize
     * @param {string} options.validationMode none, standard, other, or consigned
     * @param {Object} options.numericRanges optional inclusive numeric bounds by column
     */
    function configureSheet(options) {
        var state = { invalidCells: {}, invalidRows: {}, validationActive: false };
        var originalBeforeChange = this.cfg.beforeChange;
        var originalAfterChange = this.cfg.afterChange;
        var originalAfterRenderer = this.cfg.afterRenderer;
        var originalAfterInit = this.cfg.afterInit;
        this.cfg.lat5ValidationMode = options.validationMode;
        this.cfg.lat5ValidationRanges = options.numericRanges || {};
        this.cfg.contextMenu = { items: commonItems(options) };
        this.cfg.beforeChange = function (changes, source) {
            sanitizeNumericChanges(changes, options.numericColumns);
            if (typeof originalBeforeChange === "function") originalBeforeChange.apply(this, arguments);
        };
        this.cfg.afterChange = function (changes, source) {
            if (source !== "loadData" && state.validationActive) refreshRequiredState(this, state, options.validationMode, options.numericRanges);
            if (typeof originalAfterChange === "function") originalAfterChange.apply(this, arguments);
        };
        this.cfg.afterInit = function () {
            state.validationActive = hasServerErrorMarker(this);
            registry[this.id] = { sheet: this, state: state, options: options };
            installNavigationHook();
            if (typeof originalAfterInit === "function") originalAfterInit.apply(this, arguments);
            refreshRequiredState(this, state, options.validationMode, options.numericRanges);
        };
        this.cfg.afterRenderer = function (TD, row, col) {
            if (typeof originalAfterRenderer === "function") originalAfterRenderer.apply(this, arguments);
            var invalid = state.validationActive && !!state.invalidCells[row + ":" + col];
            var invalidRow = state.validationActive && !!state.invalidRows[row];
            toggleClass(TD, REQUIRED_CELL_CLASS, invalid);
            toggleClass(TD.parentNode, REQUIRED_ROW_CLASS, invalidRow);
            if (state.validationActive) {
                toggleClass(TD.parentNode, "error", invalidRow);
                toggleClass(TD.parentNode, "grid-error", invalidRow);
            }
        };
    }

    window.inventorySheetExtender = function () { configureSheet.call(this, { includeRowActions: false, deleteValue: "", clearEntireRow: false, columnCount: 8, numericColumns: [1, 2, 3, 4, 5, 6], validationMode: "none" }); };
    window.filingSheetExtender = function () { configureSheet.call(this, { includeRowActions: true, deleteValue: "Delete Row", clearEntireRow: false, columnCount: 3, numericColumns: [1, 2], numericRanges: { 1: { min: 0, max: new Date().getFullYear() }, 2: { min: 0, max: 9999999999 } }, validationMode: "standard" }); };
    window.otherFilingSheetExtender = function () { configureSheet.call(this, { includeRowActions: true, deleteValue: "Delete Row", clearEntireRow: false, columnCount: 4, numericColumns: [2, 3], numericRanges: { 2: { min: 0, max: new Date().getFullYear() }, 3: { min: 0, max: 9999999999 } }, validationMode: "other" }); };
    window.section5SheetExtender = function () { configureSheet.call(this, { includeRowActions: true, deleteValue: "", clearEntireRow: true, columnCount: 6, numericColumns: [4, 5], numericRanges: { 4: { min: 0, max: 999 }, 5: { min: 0, max: 9999999999 } }, validationMode: "consigned" }); };
    window.sheetExtender = window.filingSheetExtender;
}(window));
