package com.svlogic.opoppr.forms.lat5;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.function.ToLongFunction;

import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import com.svlogic.opoppr.annotation.CurrentForm;
import com.svlogic.opoppr.forms.EditableForm;
import com.svlogic.opoppr.model.Form;
import com.svlogic.opoppr.model.NoaPpLat5Inventories;

/**
 *
 * @author David
 */
@Named("lat5Section1")
@ConversationScoped
public class Section1 extends EditableForm implements Serializable {
    static private final String[] monthNames = {
            "January",
            "February",
            "March",
            "April",
            "May",
            "June",
            "July",
            "August",
            "September",
            "October",
            "November",
            "December"
    };

    static private final String[] inventoryDataKeys = {
            "01",
            "02",
            "03",
            "04",
            "05",
            "99"
    };

    public class InventoryRow {
        private String monthName;
        private HashMap<String, NoaPpLat5Inventories> data;

        public String getMonthName() {
            return monthName;
        }

        public void setMonthName(String monthName) {
            this.monthName = monthName;
        }

        public HashMap<String, NoaPpLat5Inventories> getData() {
            return data;
        }

        public void setData(HashMap<String, NoaPpLat5Inventories> data) {
            this.data = data;
        }

        public Long getTotal() {
            Long total = 0L;
            for (NoaPpLat5Inventories n : data.values()) {
                if (n.getInventoryAmt() != null) {
                    total += n.getInventoryAmt();
                }
            }
            return total;
        }
    }

    private InventoryRow[] inventoryRows = new InventoryRow[12];

    /**
     * Creates a new instance of Section1
     */
    public Section1() {
    }

    @Inject
    public void setCurrentForm(@CurrentForm Form currentForm) {
        super.setCurrentForm(currentForm);
        initializeInventoryRows();
    }

    private void initializeInventoryRows() {
        for (NoaPpLat5Inventories n : getCurrentForm().getNoaPpLat5Collection().get(0)
                .getNoaPpLat5InventoriesCollection()) {
            InventoryRow ir = inventoryRows[n.getInventoryMonth() - 1];
            if (ir == null) {
                ir = new InventoryRow();
                ir.setData(new HashMap<String, NoaPpLat5Inventories>());
                ir.setMonthName(monthNames[n.getInventoryMonth() - 1]);
                inventoryRows[n.getInventoryMonth() - 1] = ir;
            }
            ir.getData().put(n.getInventoryType(), n);
        }

        for (int i = 0; i < inventoryRows.length; ++i) {
            if (inventoryRows[i] == null) {
                inventoryRows[i] = new InventoryRow();
                inventoryRows[i].setMonthName(monthNames[i]);
                inventoryRows[i].setData(new HashMap<String, NoaPpLat5Inventories>());
            }

            for (String idk : inventoryDataKeys) {
                NoaPpLat5Inventories n = inventoryRows[i].getData().get(idk);
                if (n == null) {
                    n = new NoaPpLat5Inventories();
                    n.setInventoryMonth(i + 1);
                    n.setInventoryType(idk);
                    inventoryRows[i].getData().put(idk, n);
                }
            }
        }
    }

    /**
     * FIXED: Returns a List instead of an Array to prevent ClassCastException in pe:sheet
     */
    public List<InventoryRow> getInventoryRows() {
        if (inventoryRows == null) {
            return Collections.emptyList();
        }
        return Arrays.asList(inventoryRows);
    }

    public void setInventoryRows(InventoryRow[] inventoryRows) {
        this.inventoryRows = inventoryRows;
    }

    public String next() {
        getUserSession().storeInventories(listFromInventoryRows());
        endConversation();
        return "next";
    }

    public String previous() {
        getUserSession().storeInventories(listFromInventoryRows());
        endConversation();
        return "previous";
    }

    private List<NoaPpLat5Inventories> listFromInventoryRows() {
        List<NoaPpLat5Inventories> ret = new ArrayList<NoaPpLat5Inventories>();

        // Internal methods can use the updated getter transparently
        for (InventoryRow ir : getInventoryRows()) {
            for (NoaPpLat5Inventories n : ir.getData().values()) {
                if (n.getInventoryAmt() != null) {
                    ret.add(n);
                }
            }
        }

        return ret;
    }

    public Long getGrandTotal() {
        return getInventoryRows()
            .stream()
            .mapToLong(ir -> ir.getTotal())
            .sum();
    }

    public Double getAverage() {
        List<InventoryRow> irList = getInventoryRows();
        return irList
                .stream()
                .filter(ir -> ir.getTotal() > 0)
                .mapToLong(ir -> ir.getTotal())
                .average()
                .orElse(0.0);
    }
}


// package com.svlogic.opoppr.forms.lat5;

// import java.io.Serializable;
// import java.util.ArrayList;
// import java.util.Arrays;
// import java.util.HashMap;
// import java.util.List;
// import java.util.function.ToLongFunction;

// import jakarta.enterprise.context.ConversationScoped;
// import jakarta.inject.Inject;
// import jakarta.inject.Named;

// import com.svlogic.opoppr.annotation.CurrentForm;
// import com.svlogic.opoppr.forms.EditableForm;
// import com.svlogic.opoppr.model.Form;
// import com.svlogic.opoppr.model.NoaPpLat5Inventories;

// /**
//  *
//  * @author David
//  */
// @Named("lat5Section1")
// @ConversationScoped
// public class Section1 extends EditableForm implements Serializable {
//     static private final String[] monthNames = {
//             "January",
//             "February",
//             "March",
//             "April",
//             "May",
//             "June",
//             "July",
//             "August",
//             "September",
//             "October",
//             "November",
//             "December"
//     };

//     static private final String[] inventoryDataKeys = {
//             "01",
//             "02",
//             "03",
//             "04",
//             "05",
//             "99"
//     };

//     public class InventoryRow {
//         private String monthName;
//         private HashMap<String, NoaPpLat5Inventories> data;

//         public String getMonthName() {
//             return monthName;
//         }

//         public void setMonthName(String monthName) {
//             this.monthName = monthName;
//         }

//         public HashMap<String, NoaPpLat5Inventories> getData() {
//             return data;
//         }

//         public void setData(HashMap<String, NoaPpLat5Inventories> data) {
//             this.data = data;
//         }

//         public Long getTotal() {
//             Long total = 0L;
//             for (NoaPpLat5Inventories n : data.values()) {
//                 if (n.getInventoryAmt() != null) {
//                     total += n.getInventoryAmt();
//                 }
//             }
//             return total;
//         }
//     }

//     private InventoryRow[] inventoryRows = new InventoryRow[12];

//     /**
//      * Creates a new instance of Section1
//      */
//     public Section1() {
//     }

//     @Inject
//     public void setCurrentForm(@CurrentForm Form currentForm) {
//         super.setCurrentForm(currentForm);
//         initializeInventoryRows();
//     }

//     private void initializeInventoryRows() {
//         for (NoaPpLat5Inventories n : getCurrentForm().getNoaPpLat5Collection().get(0)
//                 .getNoaPpLat5InventoriesCollection()) {
//             InventoryRow ir = inventoryRows[n.getInventoryMonth() - 1];
//             if (ir == null) {
//                 ir = new InventoryRow();
//                 ir.setData(new HashMap<String, NoaPpLat5Inventories>());
//                 ir.setMonthName(monthNames[n.getInventoryMonth() - 1]);
//                 inventoryRows[n.getInventoryMonth() - 1] = ir;
//             }
//             ir.getData().put(n.getInventoryType(), n);
//         }

//         for (int i = 0; i < inventoryRows.length; ++i) {
//             if (inventoryRows[i] == null) {
//                 inventoryRows[i] = new InventoryRow();
//                 inventoryRows[i].setMonthName(monthNames[i]);
//                 inventoryRows[i].setData(new HashMap<String, NoaPpLat5Inventories>());
//             }

//             for (String idk : inventoryDataKeys) {
//                 NoaPpLat5Inventories n = inventoryRows[i].getData().get(idk);
//                 if (n == null) {
//                     n = new NoaPpLat5Inventories();
//                     n.setInventoryMonth(i + 1);
//                     n.setInventoryType(idk);
//                     inventoryRows[i].getData().put(idk, n);
//                 }
//             }
//         }
//     }

//     public InventoryRow[] getInventoryRows() {
//         return inventoryRows;
//     }

//     public void setInventoryRows(InventoryRow[] inventoryRows) {
//         this.inventoryRows = inventoryRows;
//     }

//     public String next() {
//         getUserSession().storeInventories(listFromInventoryRows());
//         endConversation();
//         return "next";
//     }

//     public String previous() {
//         getUserSession().storeInventories(listFromInventoryRows());
//         endConversation();
//         return "previous";
//     }

//     private List<NoaPpLat5Inventories> listFromInventoryRows() {
//         List<NoaPpLat5Inventories> ret = new ArrayList<NoaPpLat5Inventories>();

//         for (InventoryRow ir : getInventoryRows()) {
//             for (NoaPpLat5Inventories n : ir.getData().values()) {
//                 if (n.getInventoryAmt() != null) {
//                     ret.add(n);
//                 }
//             }
//         }

//         return ret;
//     }

//     public Long getGrandTotal() {
//         return Arrays.asList(getInventoryRows())
//             .stream()
//             .mapToLong(ir -> ir.getTotal())
//             .sum();
//     }

//     public Double getAverage() {
//         List<InventoryRow> irList = Arrays.asList(getInventoryRows());
//         return irList
//                 .stream()
//                 .filter(ir -> ir.getTotal() > 0)
//                 .mapToLong(ir -> ir.getTotal())
//                 .average()
//                 .orElse(0.0);
//     }
// }
