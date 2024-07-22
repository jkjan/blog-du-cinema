interface Label {
  category?: string;
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

interface UserData {
  username: string;
  nickname: string;
  profileImage?: string;
}

export type { Label, Post, ComponentData, UserData };
