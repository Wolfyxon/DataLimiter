# ItemLimiter
A simple Minecraft Spigot plugin that limits the size of items preventing book and shulker bans.  
Supported on `1.14` and higher.  
Tested on: `1.19.3` `1.19.4`

## Features
- Limiting book page size
- Removing items or their data exceeding the limit on drop, pickup, book edit and join
- High configurability and nice explanation of every setting
- Manual size checking of an item in `/datasize`

## Commands
### /datalimiter
Main command. Displays only plugin and author info if no arguments are specified.
#### /datalimiter reload
Reloads the plugin's config.  
Permission: `datalimiter.reload`

### /datasize
Shows size of the currently held item in bytes.  
Permission `datalimiter.size`

## Dedication
I made this plugin especially for a Polish server `crafted.pl` because I got angry that the admins blocked shulker boxes and books because of people using them to ban people. However books and shulkers were almost necessary on that server because of economy.  
EDIT: GUYS, THE OWNER SAID THAT HE MIGHT USE IT WAHOOO  
Edit2: he ghosted me nooo :(

## TODO
- Proper item scanning and removing on inventory click (disabled for default because it's buggy)
- Alerts
