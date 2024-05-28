import MovieDefinition from "../pages/MovieDefinition.vue";
import MovieHome from "../pages/MovieHome.vue";
import MovieInfo from "../pages/MovieInfo.vue";
import MovieForum from "../pages/MovieForum.vue";

const routes = [
  { path: "/", component: MovieHome },
  { path: "/what-is-movie", component: MovieDefinition },
  { path: "/info", component: MovieInfo },
  { path: "/forum", component: MovieForum },
];

export { routes };
