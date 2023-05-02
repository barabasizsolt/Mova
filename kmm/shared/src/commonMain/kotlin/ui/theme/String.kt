package ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf

val LocalStrings = staticCompositionLocalOf { AppString() }

/*
* TODO: Fast workaround for string resources, don't use it in production.
* Use: [https://github.com/icerockdev/moko-resources]
* */
@Immutable
data class AppString(
    val trailer: String = "Trailer",
    val favourites: String = "Favourites",
    val dismiss: String = "Dismiss",
    val or: String = "or",
    val orContinueWith: String = "or continue with",
    val signInWithPassword: String = "Sign in with password",
    val noAccount: String = "Don't have an account?",
    val signUp: String = "Sign Up",
    val letsYouIn: String = "Let's you in",
    val continueWithGoogle: String = "Continue with Google",
    val continueWithFacebook: String = "Continue with Facebook",
    val email: String = "Email",
    val password: String = "Password",
    val allContent: String = "All Content",
    val getStarted: String = "Get Started",
    val pageTitle1: String = "Welcome to Mova",
    val pageSubtitle1: String = "The best movie streaming app of the century to make your day great!",
    val pageTitle2: String = "Lorem Ipsum",
    val pageSubtitle2: String = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
    val pageTitle3: String = "Ipsum Lorem",
    val pageSubtitle3: String = "Morbi tempus consequat lectus, quis tincidunt diam efficitur non.",
    val snackBarErrorMessage: String = "Oops, something went wrong.",
    val snackBarActionLabel: String = "Try again",
    val noResultFound: String = "No Result Found",
    val notFound: String = "Not Found",
    val notFoundText: String = "Sorry, the keyword you entered could not be found. Try to check again or search with other keywords.",
    val sortFilter: String = "Sort & Filter",
    val categories: String = "Categories",
    val regions: String = "Regions",
    val genres: String = "Genres",
    val sort: String = "Sorting Criteria",
    val apply: String = "Apply",
    val reset: String = "Reset",
    val popularMovies: String = "Popular Movies",
    val morePopularMovies: String = "More Popular Movies",
    val popularPeople: String = "Popular People",
    val nowPlayingMovies: String = "Now Playing Movies",
    val moreNowPlayingMovies: String = "More Now Playing Movies",
    val topRatedMovies: String = "Top Rated Movies",
    val moreTopRatedMovies: String = "More Top Rated Movies",
    val seeAll: String = "See All",
    val scrollUp: String = "Scroll Up",
    val home: String = "Home",
    val explore: String = "Explore",
    val profile: String = "Profile",
    val loginToYourAccount: String = "Login to Your Account",
    val createYourAccount: String = "Create Your Account",
    val alreadyHaveAnAccount: String = "Already have an account?",
    val signIn: String = "Sign in",
    val createdAt: String = "Created At",
    val searchMovies: String = "Search Movies",
    val searchTvSeries: String = "Search Tv Series",
    val logOut: String = "Logout",
    val movies: String = "Movies",
    val tvSeries: String = "Tv Series"
)
