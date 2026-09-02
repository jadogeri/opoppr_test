#! /bin/bash

# set up sendmail with default options.
sendmailconfig <<EOF
y
y
y
EOF

update-rc.d sendmail enable