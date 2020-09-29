# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.19.0] - ???

### Added

- Added introductory message to /playerplot command
- Added /playerplot info command

### Changed

- Improved help command formatting
- Changed help command styling to match plugin theme

## [1.18.0] - 2020-09-14

### Added

- support for all subversions v1.8_R1 to v1.16_R2
- /pplot as an alternative root command
  - root command option to config.yml
- extended remote commands
- -l argument to extend duration of scan<br> 
  `/plot scan -l`
- database keep alive query
- 1.16 texture pack 
- plot teleportation<br>
- commands
  >/toplot [plot] -  teleport to plot<br> 
  >/plot setspawn - set spawn point
- permission
  >playerplot.teleport

### Changed

- versioning to semantic versioning
- remote plot format
  >/plot @[plot] free<br>
  >/plot @[plot] upgrade<br>
  >/plot @[plot] rename [name]<br>
  >. . .
- world border not shown when not inside radius
- border will push player inside if used on edge of plot
- en.yml language file
- format of plots when listed with /plot list
  - now more clear how used plots are calculated


### Removed

- protection for enchanting table, minecarts, and ender chests

### Fixed

- players can get outside of scanner bounds and cross real world border
- old scan ends newly started scans early
- database timeout issue preventing reads/writes
- zoning bug that prevented plots from being properly read from cache after using the /plot setcenter command in a far away location

  
<!--

Added - for new features.
Changed - for changes in existing functionality.
Deprecated - for soon-to-be removed features.
Removed - for now removed features.
Fixed - for any bug fixes.
Security - in case of vulnerabilities. 

 -->