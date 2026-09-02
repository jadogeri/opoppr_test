workspace "OPOPPR" "Orleans Parish Online Personal Property Reporting" {

    !identifiers hierarchical

    model {
        taxPreparer = person "Tax Prepaper" {
            description "A user who prepares and submits property tax reports online."
            tags "Person"
        }

        administrator = person "Administrator" {
            description "A user with full administrative access to the system. Administrators can manage users, purge the OPOPPR database, import data, and run reports."
            tags "Person"
        }

        superUser = person "Super User" {
            description "A user who has elevated privileges to run reports and flush the EntityManager cache."
            tags "Person"
        }

        opopprSystem = softwareSystem "OPOPPR"  {
            description "The Orleans Parish Online Personal Property Reporting system (OPOPPR) allows tax preparers to submit personal property tax reports online. The tools support preparation and submission of LAT5 forms."
            web = container "OPOPPR UI" {
                description "Displays the user interface for the OPOPPR application."
                technology "JavaScript, HTML5"
                tags "UI"
            }
            wa = container "OPOPPR Web Application" {
                description "Renders the OPOPPR UI and processes user actions."
                technology "Java, JSF, JPA"
                tags "AppServer"
            }
            db = container "OPOPPR Database" {
                description "The database schema for the OPOPPR application."
                technology "MySQL"
                tags "Database"
                
                dbSchema = component "OPOPPR Databsae Schema"
            }
        }

        taxReportingSystem = softwareSystem "Tax Reporting System" {
            description "The Tax Reporting System is used by the Orleans Parish Tax Assessor's Office to manage and process property tax reports that were exported from OPOPPR."
            tags "External Software System"
        }

        taxPreparer -> opopprSystem.web "Prepares and submits property tax reports using"
        administrator -> opopprSystem.web "Performs administrative functions using"
        administrator -> opopprSystem.db.dbSchema "Perform administrative functions on"
        administrator -> taxReportingSystem "Imports OPOPPR data to"
        superUser -> opopprSystem.web "Runs reports and flushes the EntityManager cache using"
        opopprSystem.web -> opopprSystem.wa "Is rendered by"
        opopprSystem.wa -> opopprSystem.db.dbSchema "Reads from and writes to"
    }

    views {
        systemLandscape opopprSystemLandscape "OPOPPR_Landscape_Diagram" {
          include * 
          autoLayout
        }

        systemContext opopprSystem "OPOPPR_Context_Diagram" {
            include *
            autolayout
        }

        container opopprSystem "OPOPPR_Container_Diagram" {
            include *
            autolayout
        }

        component opopprSystem.db "OPOPPR_DB_Component_Diagram" {
            include *
            autolayout
        }

        image opopprSystem.db.dbSchema
            image "https://raw.githubusercontent.com/SVLogic2021/orleans-parish-online-personal-property-reporting/refs/heads/master/Design/diagrams/OPOPPR%20-%20ERD.png?token=GHSAT0AAAAAADLWTUHM3CA3HCU7VDPXA75M2GZ6R3A"
            title "OPOPPR Database Schema"
        }
        
        styles {
            element "Element" {
                color #ffffff
            }
            element "Person" {
                background #08427b
                shape person
            }
            element "Software System" {
                background #1168bd
            }
            element "External Software System" {
                background #999999
            }
            element "AppServer" {
                background #438dd5
            }
            element "UI" {
                shape webbrowser
                background #438dd5
            }
            element "Database" {
                shape cylinder
                background #438dd5
            }
            element "Component" {
                shape component
                background #85bbf0
                color #000000
            }
        }
    }

    configuration {
        scope softwaresystem
    }
}