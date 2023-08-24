# ItemLimiter
A simple Minecraft Spigot plugin that limits the size of items preventning book and shulker bans.  
Supported on `1.14` and higher.  
Tested on: `1.19.3` `1.19.4`
## Features
- Limiting book page size
- Removing items exceeding the limit on pick up, click in inventory and join
- High configurability and nice explaination of every setting
- Manual size checking of an item if `/datasize`

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
Hopefully the owner adds it.

## TODO
- Alerts
