<!-- PROJECT SHIELDS -->
<!--
*** I'm using markdown "reference style" links for readability.
*** Reference links are enclosed in brackets [ ] instead of parentheses ( ).
*** See the bottom of this document for the declaration of the reference variables
*** for contributors-url, forks-url, etc. This is an optional, concise syntax you may use.
*** https://www.markdownguide.org/basic-syntax/#reference-style-links
-->
[![Version][version-shield]][version-url]
[![Download][download-shield]][download-url]
[![Tested Versions][tested-shield]][tested-url]
[![MIT License][license-shield]][license-url]
[![Discord][discord-shield]][discord-url]

<!-- PROJECT LOGO -->
<br/>
<div align="center">
  <a href="https://gitlab.com/sword7/playerplot">
    <img src="https://assets.gitlab-static.net/uploads/-/system/project/avatar/11067249/playerplot.png" alt="Logo" width="80" height="80">
  </a>

  ### Player Plot <br><sub>*Build without fear!*</sub>
  **[Explore the docs »](https://gitlab.com/sword7/playerplot)**
  <br/>
  [Report Bug](https://discord.com/invite/hKTXQBH)
  ·
  [Request Feature](https://discord.com/invite/hKTXQBH)
</div>

<!-- TABLE OF CONTENTS -->
## Table of Contents

* [About the Project](#about-the-project)
  * [Built With](#built-with)
* [Getting Started](#getting-started)
  * [Prerequisites](#prerequisites)
  * [Installation](#installation)
* [Usage](#usage)
  * [Protection](#protection)
  * [Upgrading](#upgrading)
  * [Dynmap](#dynmap)
* [Roadmap](#roadmap)
* [Contributing](#contributing)
* [License](#license)
* [Contact](#contact)
* [Acknowledgements](#acknowledgements)

<!-- ABOUT THE PROJECT -->
## About The Project

![Player Plot Screen Shot](https://gitlab.com/sword7/playerplot/-/blob/master/images/cover.png)

Player Plot is a self-serve protection plugin for Minecraft. It allows users to unlock and claim protected regions. The plugin is extremely scalable thanks to plots beings stored in grid zones. The plugin is also very user friendly because any plot's border can be made visible using the /plot scan command.

#### Built With

* [Spigot](https://www.spigotmc.org/)


<!-- GETTING STARTED -->
## Getting Started

The lastest version of Player Plot is available on [SpigotMC](https://www.spigotmc.org/resources/player-plot.68033/).

#### Prerequisites

![Tested Versions][tested-shield]

Player Plot requires a Minecraft server running version 1.8 to 1.6.

#### Installation

Player Plot is installed like any other Spigot plugin. If you need a refresher you can refer to [Installation](https://gitlab.com/sword7/playerplot/-/wikis/setup/installation).

<!-- USAGE EXAMPLES -->
## Usage

A plot is a protected zone that belongs to an owning player. Plots can be managed by using /plot commands while standing in the target plot. Plots can also be edited remotely by using the /plot @[plot] format.

#### Protection

Plots offer robust protection from both players and the elements. Plots are protected from the following

- Unauthorized building
- Unauthorized breaking
- Unauthorized use (ie chests/doors)
- Unauthorized harming of animals
- Explosions
- Fire-spread
- Outside pistons
- Outside dragon egg teleportion
- Outside water/lava flow
- Farmland Trampling
- And More!

#### Upgrading

When a plot is upgraded, its new side length is calculated so that the total plot size increases by the area of a single plot. For example, upgrading a plot once will result in a plot that is twice the size of the original. Upgrading a plot twice will result in a plot that is three times the size of the original. A plot is consumed every time a plot is upgraded.

Downgrading a plot will reduce the size back by the area of a single plot. When a plot is fully downgraded, it will be the same size and in the exact same position as the original plot. The plots that were consumed during the upgrade process will be returned back to the player.

#### Plot Deeds

Players unlock new plots by obtaining and right clicking on a plot deed. The plugin comes with an optional resource pack that adds custom textures to plot deeds.

![Plot Deed](https://gitlab.com/sword7/playerplot/-/blob/master/images/plot-deed.png)

You can download the resource pack [here](https://gitlab.com/sword7/playerplot/-/wikis/misc/resource-pack).

#### Dynmap

Player Plot is compatible with dynmap. Configure the dynmap with the options in the config.yml file.

![Plot Deed](https://gitlab.com/sword7/playerplot/-/blob/master/images/dynmap.png)

_For more information, please refer to the [Documentation](https://gitlab.com/sword7/playerplot/-/wikis/home)._

<!-- ROADMAP -->
## Roadmap

See the Player Plot [Trello board](https://trello.com/b/SBzUNtZC/player-plot) for a list of proposed features and known issues.

<!-- CONTRIBUTING -->
## Contributing

Contributions are what make the open source community such an amazing place to be learn, inspire, and create. Any contributions you make are greatly appreciated.

See `CONTRIBUTING` for more information.

<!-- LICENSE -->
## License

Distributed under the MIT License. See `LICENSE` for more information.

<!-- CONTACT -->
## Contact

sword7 - sword#5439 - devsword7@gmail.com

Project Link: [https://gitlab.com/sword7/playerplot](https://gitlab.com/sword7/playerplot)

<!-- ACKNOWLEDGEMENTS -->
## Acknowledgements

* [All Spigot NMS](https://github.com/Jacxk/all-spigot-nms)
* [Crowdin](https://crowdin.com/)
* [Dynmap](https://github.com/webbukkit/dynmap)
* [GitLab](https://gitlab.com)
* [Shields IO](https://shields.io/)
* [XSeries](https://github.com/CryptoMorin/XSeries)


<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[version-shield]: https://img.shields.io/spiget/version/68033?label=&labelColor=EE22EE&color=FF55FF
[version-url]: https://www.spigotmc.org/resources/player-plot.68033/
[download-shield]: https://img.shields.io/spiget/downloads/68033?&color=efb61c&style=flat-square&logo=image%2Fx-icon%3Bbase64%2CAAABAAEAEBAQAAAAAAAoAQAAFgAAACgAAAAQAAAAIAAAAAEABAAAAAAAgAAAAAAAAAAAAAAAEAAAAAAAAAAAAAAAAND%2FAOhGOgA%2F6OIAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAiAAAAAAAAACIAAAAAAAAAIgAAAAAAAAAAAAAAAAAAABEAAAAzMQABEQAAARMzEBERARERETMxERAAAAARMzEAAAAAAAETMwAAAAAAABEwAAAAAAAAERAAAAAAAAABAAAAAAAAAAEAAAAAAAAAAAAAAAAAAAAAAAAAD%2F%2BQAA%2F%2FkAAP%2F5AAD%2F8AAA%2BDAAAPAgAAAAAAAAAAEAAAADAADwDwAA%2FB8AAPwfAAD8HwAA%2Fj8AAP4%2FAADwBwAA
[download-url]: https://www.spigotmc.org/resources/player-plot.68033/
[license-shield]: https://img.shields.io/badge/license-MIT-blue?style=flat-square
[tested-shield]: https://img.shields.io/spiget/tested-versions/68033?style=flat-square
[tested-url]: https://www.spigotmc.org/resources/player-plot.68033/
[license-url]: https://gitlab.com/sword7/playerplot/-/blob/master/LICENSE
[discord-shield]: https://img.shields.io/discord/623658924079448074?label=&style=flat&labelColor=697ec4&color=8196de&logoColor=ffffff&logo=Discord&logoWidth=20
[discord-url]: https://discord.com/invite/hKTXQBH
[product-screenshot]: images/screenshot.png
