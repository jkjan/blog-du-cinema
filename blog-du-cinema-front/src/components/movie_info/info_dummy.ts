import { Content, Index } from "../../types.ts";

const movieInfoCategories = [
  { name: "영화의 역사", category: "history" },
  { name: "다양한 장르", category: "genre" },
  { name: "영화 방법론", category: "methodology" },
  { name: "관련 사이트", category: "website" },
  { name: "단어 사전", category: "dictionary" },
];

const indexForCategory: { [m: string]: Index[] } = {
  history: [
    { key: "a", label: "History 1" },
    { key: "b", label: "History 2" },
    { key: "c", label: "History 3" },
  ],
  genre: [
    { key: "d", label: "Genre 1" },
    { key: "e", label: "Genre 2" },
    { key: "f", label: "Genre 3" },
  ],
  methodology: [
    { key: "m1", label: "Methodology 1" },
    { key: "m2", label: "Methodology 2" },
    { key: "m3", label: "Methodology 3" },
  ],
  website: [
    { key: "s1", label: "website 1" },
    { key: "s2", label: "website 2" },
    { key: "s3", label: "website 3" },
  ],
  dictionary: [
    { key: "g", label: "Dict 1" },
    { key: "h", label: "Dict 2" },
    { key: "i", label: "Dict 3" },
  ],
};

const contentForIndexKey: {
  [indexKey: string]: Content[];
} = {
  // 역사
  a: [{ description: "역사 설명 1" }],
  b: [{ description: "역사 설명 2" }],
  c: [{ description: "역사 설명 3" }],

  // 장르
  d: [{ description: "장르 설명 1" }],
  e: [{ description: "장르 설명 2" }],
  f: [{ description: "장르 설명 3" }],

  // 방법론
  m1: [{ description: "방법론 설명 1" }],
  m2: [{ description: "방법론 설명 2" }],
  m3: [{ description: "방법론 설명 3" }],

  // 사이트
  s1: [{ description: "사이트 설명 1" }],
  s2: [{ description: "사이트 설명 2" }],
  s3: [{ description: "사이트 설명 3" }],

  // 사전
  g: [
    { vocab: "Word G1", description: "Example G1" },
    { vocab: "Word G2", description: "Example G2" },
    { vocab: "Word G3", description: "Example G3" },
  ],
  h: [
    { vocab: "Word H1", description: "Example H1" },
    { vocab: "Word H2", description: "Example H2" },
    { vocab: "Word H3", description: "Example H3" },
  ],
  i: [
    { vocab: "Word I1", description: "Example I1" },
    { vocab: "Word I2", description: "Example I2" },
    { vocab: "Word I3", description: "Example I3" },
  ],
};

export { indexForCategory, contentForIndexKey, movieInfoCategories };
