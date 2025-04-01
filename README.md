<!-- Improved compatibility of back to top link: See: https://github.com/othneildrew/Best-README-Template/pull/73 -->
<a id="readme-top"></a>
<!--
*** Thanks for checking out the Best-README-Template. If you have a suggestion
*** that would make this better, please fork the repo and create a pull request
*** or simply open an issue with the tag "enhancement".
*** Don't forget to give the project a star!
*** Thanks again! Now go create something AMAZING! :D
-->



<!-- PROJECT SHIELDS -->
<!--
*** I'm using markdown "reference style" links for readability.
*** Reference links are enclosed in brackets [ ] instead of parentheses ( ).
*** See the bottom of this document for the declaration of the reference variables
*** for contributors-url, forks-url, etc. This is an optional, concise syntax you may use.
*** https://www.markdownguide.org/basic-syntax/#reference-style-links
-->
[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![Unlicense License][license-shield]][license-url]
[![LinkedIn][linkedin-shield]][linkedin-url]



<!-- PROJECT LOGO -->
<br />
<div align="center">
  <h3 align="center">Cineman - Movie App</h3>

  <p align="center">
    A modern Android movie application powered by TMDB API with Jetpack Compose
  </p>
</div>



<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#features">Features</a></li>
    <li><a href="#architecture">Architecture</a></li>
    <li><a href="#roadmap">Roadmap</a></li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#contact">Contact</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About The Project

Cineman is a feature-rich Android application that allows users to discover, track, and manage their favorite movies. Built with modern Android development practices and TMDB API integration.

Key Features:
* Browse popular, trending, and upcoming movies
* Create and manage custom movie playlists
* Rate movies and add them to favorites/watchlist
* Search movies with real-time results
* Watch movie trailers
* View movie details including cast, ratings, and production information

### Built With

* ![Kotlin](https://img.shields.io/badge/kotlin-%237F52FF.svg?style=for-the-badge&logo=kotlin&logoColor=white)
* ![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-4285F4.svg?style=for-the-badge&logo=jetpackcompose&logoColor=white)
* ![Hilt](https://img.shields.io/badge/Hilt-2196F3.svg?style=for-the-badge&logo=android&logoColor=white)
* ![Retrofit](https://img.shields.io/badge/Retrofit-48B983.svg?style=for-the-badge&logo=square&logoColor=white)
* ![Paging 3](https://img.shields.io/badge/Paging%203-3DDC84.svg?style=for-the-badge&logo=android&logoColor=white)

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- GETTING STARTED -->
## Getting Started

To get a local copy up and running follow these steps:

### Prerequisites

* Android Studio Electric Eel or newer
* JDK 11 or newer
* Android SDK with minimum API 24

### Installation

1. Get a free API Key from [TMDB](https://www.themoviedb.org/documentation/api)
2. Clone the repo
   ```sh
   git clone https://github.com/ThaiMinhNguyen/CineMan.git
   ```
3. Enter your API key in `local.properties`
   ```properties
   tmdb_api_key=YOUR_API_KEY
   ```
4. Build and run the project

## Architecture

The app follows MVVM architecture pattern and uses:
- ViewModel for managing UI-related data
- Repository pattern for data operations
- Kotlin Flows and Coroutines for async operations
- Jetpack Compose for declarative UI
- Hilt for dependency injection
- Paging 3 for pagination
- Retrofit for API calls

### Project Structure
- `api`: Contains API services and repositories
- `entity`: Data models and entities
- `screens`: Compose UI components
- `viewmodel`: ViewModels for managing UI state
- `ui`: UI related components and themes

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- FEATURES -->
## Features

1. **Authentication**: Login with TMDB account
2. **Movie Discovery**:
   - Browse popular, trending, and upcoming movies
   - View movie details with trailers and cast information
   
3. **Personal Lists**:
   - Create, edit, and delete custom movie lists
   - Add movies to lists
   - Clear all movies from a list
   
4. **User Interactions**:
   - Rate movies
   - Mark movies as favorite
   - Add movies to watchlist
   - Share movie details

5. **Search**:
   - Search movies with real-time results
   - Sort movie results

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- ROADMAP -->
## Roadmap

- [ ] Add TV show support
- [ ] Implement offline caching
- [ ] Add personalized recommendations
- [ ] Support multiple languages
- [ ] Implement advanced filtering
- [ ] Add user profile customization

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- CONTRIBUTING -->
## Contributing

Contributions are what make the open source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

### Top contributors:

<a href="https://github.com/othneildrew/Best-README-Template/graphs/contributors">
  <img src="https://contrib.rocks/image?repo=othneildrew/Best-README-Template" alt="contrib.rocks image" />
</a>

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- LICENSE -->
## License

Distributed under the MIT License. See `LICENSE` for more information.

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- CONTACT -->
## Contact

Your Name - thaiminhnguyen2003@gmail.com

Project Link: [https://github.com/ThaiMinhNguyen/CineMan](https://github.com/ThaiMinhNguyen/CineMan)

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- ACKNOWLEDGMENTS -->
## Acknowledgments

Use this space to list resources you find helpful and would like to give credit to. I've included a few of my favorites to kick things off!

* [Choose an Open Source License](https://choosealicense.com)
* [GitHub Emoji Cheat Sheet](https://www.webpagefx.com/tools/emoji-cheat-sheet)
* [Malven's Flexbox Cheatsheet](https://flexbox.malven.co/)
* [Malven's Grid Cheatsheet](https://grid.malven.co/)
* [Img Shields](https://shields.io)
* [GitHub Pages](https://pages.github.com)
* [Font Awesome](https://fontawesome.com)
* [React Icons](https://react-icons.github.io/react-icons/search)

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io/github/contributors/othneildrew/Best-README-Template.svg?style=for-the-badge
[contributors-url]: https://github.com/ThaiMinhNguyen/CineMan/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/othneildrew/Best-README-Template.svg?style=for-the-badge
[forks-url]: https://github.com/ThaiMinhNguyen/CineMan/forks
[stars-shield]: https://img.shields.io/github/stars/othneildrew/Best-README-Template.svg?style=for-the-badge
[stars-url]: https://github.com/ThaiMinhNguyen/CineMan/stargazers
[issues-shield]: https://img.shields.io/github/issues/othneildrew/Best-README-Template.svg?style=for-the-badge
[issues-url]: https://github.com/ThaiMinhNguyen/CineMan/issues
[license-shield]: https://img.shields.io/github/license/othneildrew/Best-README-Template.svg?style=for-the-badge
[license-url]: https://www.google.com/?hl=vi
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://www.linkedin.com/in/thaiminhnguyen2003/ 
[Kotlin-badge]: https://img.shields.io/badge/kotlin-%237F52FF.svg?style=for-the-badge&logo=kotlin&logoColor=white
[Kotlin-url]: https://kotlinlang.org/
[Compose-badge]: https://img.shields.io/badge/Jetpack%20Compose-4285F4.svg?style=for-the-badge&logo=jetpackcompose&logoColor=white
[Compose-url]: https://developer.android.com/jetpack/compose
[Hilt-badge]: https://img.shields.io/badge/Hilt-2196F3.svg?style=for-the-badge&logo=android&logoColor=white
[Hilt-url]: https://dagger.dev/hilt/
[Retrofit-badge]: https://img.shields.io/badge/Retrofit-48B983.svg?style=for-the-badge&logo=square&logoColor=white
[Retrofit-url]: https://square.github.io/retrofit/ 