/* Context menus for the LAT5 PrimeFaces Extensions sheets. */
(function (window) {
    "use strict";

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

    function hasSelection(sheet) {
        return !selectionBounds(sheet);
    }

    function editCell(key, selection) {
        var range = selection && selection.length ? selection[0] : selectionBounds(this);
        if (!range) {
            return;
        }
        this.selectCell(range[0], range[1]);
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

    function deleteSelectedRows() {
        var bounds = selectionBounds(this);
        if (!bounds) {
            return;
        }

        var changes = [];
        for (var row = bounds.startRow; row <= bounds.endRow; row++) {
            // The first column is the server-side property selector. Selecting
            // Delete Row clears pptype, which the existing save flow deletes.
            changes.push([row, 0, "Delete Row"]);
        }
        this.setDataAtCell(changes, "contextMenu.deleteRow");
    }

    function commonItems(includeRowActions) {
        var items = {
            edit_cell: {
                name: "Edit Cell",
                disabled: hasSelection,
                callback: editCell
            }
        };

        if (includeRowActions) {
            items.add_row = {
                name: "Add New Row",
                callback: addNewRow
            };
            items.delete_row = {
                name: "Delete Selected Row(s)",
                disabled: hasSelection,
                callback: deleteSelectedRows
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

    function configureSheet(includeRowActions) {
        // PrimeFaces Extensions passes the Sheet widget as this and Handsontable
        // uses the callbacks below with the table instance as their this value.
        this.cfg.contextMenu = {
            items: commonItems(includeRowActions)
        };
    }

    window.inventorySheetExtender = function () {
        configureSheet.call(this, false);
    };

    window.filingSheetExtender = function () {
        configureSheet.call(this, true);
    };

    // Keep the name from code.txt available for any page that adopts the generic menu.
    window.sheetExtender = window.filingSheetExtender;
}(window));
