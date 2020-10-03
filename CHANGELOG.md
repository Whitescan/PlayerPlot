# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.19.0] - 2020-10-03

**[Warning] 1.19.0 requires the new texture pack.**<br>
**[Warning] The /plotdeed command has changed.**<br>
**[Warning] The language file has changed, delete to allow it to reload.**<br>
**[Warning] The config file has changed. delete to allow it to reload.**<br>

### Added

- Added introductory message to /playerplot command
- Added back some admin commands (/plot activity command will return in the data storage update)
- Added commands:
  >/playerplot info *- get plugin info*<br>
  /playerplot reload *- reload player plot config (plot/user data will not reload yet)*<br>
  /playerplot  update *- check for updates*<br>
  /writedeed *- write plot deeds*<br>
  /allplots *- view all plots*<br>
  /delplot *- delete any plot*<br>
  /delplotconfirm *- confirm deletion*<br>
  /delplotcancel *- confirm deletion*<br>
- Added permission nodes:
  >playerplot.reload *- grants access to /playerplot reload*<br> 
  playerplot.update *- grants access to /playerplot update*<br> 
  playerplot.write *- grants access to /writedeed* (default true)<br> 
  playerplot.admin.* *- grants all Player Plot admin permissions*<br> 
  playerplot.admin.view *- grants access to /allplots*<br>
  playerplot.admin.delete *- grants access to plot deletion commands*<br>
  playerplot.plot.*: *-grants access to all plot commands* (default true)<br>
  playerplot.plot.scan: *- grants access to /plot scan*<br>
  playerplot.plot.claim: *- grants access to /plot claim*<br>
  playerplot.plot.list: *- grants access to /plot list*<br>
  playerplot.plot.flist: *- grants access to /plot flist*<br>
  playerplot.plot.info: *- grants access to /plot info*<br>
  playerplot.plot.trust: *- grants access to /plot trust*<br>
  playerplot.plot.untrust: *- grants access to /plot untrust*<br>
  playerplot.plot.upgrade: *- grants access to /plot upgrade*<br>
  playerplot.plot.downgrade: *- grants access to /plot downgrade*<br>
  playerplot.plot.setcenter: *- grants access to /plot setcenter*<br>
  playerplot.plot.setspawn: *- grants access to /plot setspawn*<br>                  
- Added swamp, mountain, and ocean plot deeds
- Added plot pvp option to config
- Added permission node for all plot actions
- Added plot world blacklist to config

### Changed

- Improved help command formatting
- Changed help command styling to match plugin theme
- Updated language file
- Improved autocomplete
- Updated Player Plot texture pack
- Plot deeds now uses translation file for "+1 plot"
- Changed /plotdeed command structure
- Changed CustomModelData values in [resource pack](https://gitlab.com/sword7/playerplot/-/wikis/misc/resource-pack)
- Refactored code (preparing for data storage update)
- Improved styling for plot usage indicator to make the calculation more clear
 
### Fixed

- Fixed issue where plots would protect some hostile mobs from outsiders
 
## [1.18.0] - 2020-09-14

### Added

- Support for all sub-versions v1.8_R1 to v1.16_R2
- /pplot as an alternative root command
  - root command option to config.yml
- Extended remote commands
- -l argument to extend duration of scan<br> 
  `/plot scan -l`
- Database keep-alive query
- 1.16 texture pack 
- Plot teleportation<br>
- Added commands:
  >/toplot [plot] *- teleport to plot*<br> 
  /plot setspawn *- set spawn point*<br> 
- Added permission nodes:
  >playerplot.teleport *- grants access to teleportation commands*<br> 

### Changed

- Versioning to semantic versioning
- Remote plot format
  >/plot @[plot] free<br>
  /plot @[plot] upgrade<br>
  /plot @[plot] rename [name]<br>
  . . .
- World border not shown when not inside radius
- Border will push player inside if used on edge of plot
- en.yml language file
- Format of plots when listed with /plot list
  - now more clear how used plots are calculated


### Removed

- Protection for enchanting table, minecarts, and ender chests

### Fixed

- Players can get outside of scanner bounds and cross real world border
- Old scan ends newly started scans early
- Database timeout issue preventing reads/writes
- Zoning bug that prevented plots from being properly read from cache after using the /plot setcenter command in a far away location

  
<!--

Added - for new features.
Changed - for changes in existing functionality.
Deprecated - for soon-to-be removed features.
Removed - for now removed features.
Fixed - for any bug fixes.
Security - in case of vulnerabilities. 

 -->