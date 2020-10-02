# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.19.0] - ???

**[Warning] 1.19.0 requires the new texture pack.**
**[Warning] The /plotdeed command has changed.**
**[Warning] The language file has changed, delete to allow it to reload.**

### Added

- Added introductory message to /playerplot command
- Added Commands:
  >/playerplot info - get plugin info
  >/playerplot reload - reload player plot config (plot/user data will not reload yet)
  >/playerplot  update - check for updates
  >/writedeed - write plot deeds
- Added permission nodes:
  >playerplot.reload - grants access to /playerplot reload
  >playerplot.update - grants access to /playerplot update
  >playerplot.write - grants access to /writedeed
- Added swamp, mountain, and ocean plot deeds

### Changed

- Improved help command formatting
- Changed help command styling to match plugin theme
- Updated language file
- Improved autocomplete
- PlayerPlot texture pack
- Plot deeds now uses translation file for "+1 plot"
- Changed /plotdeed command structure
- Changed CustomModelData values in [resource pack](https://gitlab.com/sword7/playerplot/-/wikis/misc/resource-pack)
- Refactored code (preparing for data storage update)
 
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
- Commands
  >/toplot [plot] -  teleport to plot<br> 
  >/plot setspawn - set spawn point
- Permission
  >playerplot.teleport

### Changed

- Versioning to semantic versioning
- Remote plot format
  >/plot @[plot] free<br>
  >/plot @[plot] upgrade<br>
  >/plot @[plot] rename [name]<br>
  >. . .
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