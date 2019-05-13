export class Book {
  id: string;
  name: string;
  price: number;
  authors: User[];
  quantity: number;
  publishYear: string;
}

export class User {
  id: string;
  name: string;
}

export class Log {
  id: string;
  date: string;
  book: Book;
  user: User;
}
