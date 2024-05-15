interface Index {
  key: string;
  label: string;
}

interface Content {
  description: string;
  vocab?: string;
}

interface ComponentData {
  category: string;
  indexes: Index[];
  content: Content[];
  nowKey: string;
}

export type { Index, Content, ComponentData };
