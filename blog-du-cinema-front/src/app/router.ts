import MovieDefinition from "../pages/MovieDefinition.vue";
import MovieHome from "../pages/MovieHome.vue";
import MovieInfo from "../pages/MovieInfo.vue";
import MovieForum from "../pages/MovieForum.vue";
import SignUp from "../pages/SignUp.vue";
import SignIn from "../pages/SignIn.vue";

const routes = [
  { path: "/", component: MovieHome },
  { path: "/what-is-movie", component: MovieDefinition },
  { path: "/info", component: MovieInfo },
  { path: "/forum", component: MovieForum },
  { path: "/sign-up", component: SignUp },
  { path: "/sign-in", component: SignIn },
];

export { routes };
