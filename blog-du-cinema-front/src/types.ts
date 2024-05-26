interface Label {
  labelId: number;
  labelNum: string;
  labelName: string;
}

interface Post {
  title: string;
  contentHtml?: string;
}

interface ComponentData {
  category: string;
  labels: Label[];
  post: Post[];
  nowLabelIndex: number;
}

export type { Label, Post, ComponentData };
