import MovieDefinition from "../components/MovieDefinition.vue";
import MovieHome from "../components/MovieHome.vue";
import MovieInfo from "../components/movie_info/MovieInfo.vue";
import MovieForum from "../components/movie_forum/MovieForum.vue";

const routes = [
  { path: "/", component: MovieHome },
  { path: "/what-is-movie", component: MovieDefinition },
  { path: "/info", component: MovieInfo },
  { path: "/forum", component: MovieForum },
];

export { routes };
