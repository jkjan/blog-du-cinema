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
  userId: string;
  username: string;
  profileImage?: string;
  jwtToken: string;
}

interface UserState {
  isLoggedIn: boolean;
  nowUserData?: UserData;
}

export type { Label, Post, ComponentData, UserData, UserState };
