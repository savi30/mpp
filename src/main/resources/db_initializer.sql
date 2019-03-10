create table books (
	id varchar(255) primary key,
    title varchar(255) not null,
    publishYear datetime
);
create table authors (
	id varchar(255) primary key,
    author_name varchar(255)
);
create table users (
	id varchar(255) primary key,
    username varchar(255)
);
create table inventory (
	id bigint primary key auto_increment,
	book_id varchar(255) not null,
    quantity int not null,
    price double not null,
    foreign key(book_id) references books(id) on delete cascade on update cascade
);
create table store_logs (
	transaction_id bigint primary key auto_increment,
	user_id varchar(255) not null,
    book_id varchar(255) not null,
    transaction_date datetime,
    foreign key(user_id) references users(id),
    foreign key(book_id) references books(id)
);
create table authors_books(
	author_id varchar(255) not null,
    book_id varchar(255) not null,
    foreign key(author_id) references authors(id),
    foreign key(book_id) references books(id),
    primary key(author_id, book_id)
);
insert into users(id, username) values
	("1", "rocky"),
    ("2", "archer"),
    ("3", "jerry"),
    ("4", "lana"),
    ("5", "homer");
    
insert into authors values 
	("1", "Robert C. Martin"),
    ("2", "George Orwell"),
    ("3", "Amie Kaufman"),
    ("4", "Jay Kristoff");
    
insert into books values 
	("1", "1984", str_to_date('1949-08-01', '%Y-%m-%d')),
	("2", "Clean Code", str_to_date("2008-11-08", '%Y-%m-%d')),
	("3", "Illuminae", str_to_date("2015-10-20", '%Y-%m-%d'));
    
insert into inventory(book_id, quantity, price) values 
	("1", 4, 4.5),
    ("2", 3, 10.9),
    ("3", 0, 8.4);
    
insert into authors_books (author_id, book_id) values 
	("1", "2"),
    ("2", "1"),
    ("3", "3"),
    ("4", "3");
    
insert into store_logs (user_id, book_id, transaction_date) values
	("1", "1", str_to_date("2018-11-01",'%Y-%m-%d')),
	("1", "2", str_to_date("2019-02-12",'%Y-%m-%d')),
	("2", "1", str_to_date("2018-11-03",'%Y-%m-%d')),
	("3", "3", str_to_date("2018-07-20",'%Y-%m-%d'));