insert into province(id,name) values (1,'北京');
insert into province(id,name) values (2,'天津');
insert into province(id,name) values (3,'贵州');
insert into province(id,name) values (4,'上海');
insert into province(id,name) values (5,'重庆');
insert into province(id,name) values (6,'江西');
insert into province(id,name) values (7,'河北');
insert into province(id,name) values (8,'四川');
insert into province(id,name) values (9,'云南');
insert into province(id,name) values (10,'广西');
insert into province(id,name) values (11,'广州');
insert into province(id,name) values (12,'浙江');

insert into good(id,alias,name,province_id) values (1,'apple','苹果','1');
insert into good(id,alias,name,province_id) values (2,'pear','梨','2');
insert into good(id,alias,name,province_id) values (3,'phone','手机','2');
insert into good(id,alias,name,province_id) values (4,'link','线','1');
insert into good(id,alias,name,province_id) values (5,'flower','花','3');
insert into good(id,alias,name,province_id) values (6,'cup','杯子','4');
insert into good(id,alias,name,province_id) values (7,'knife','刀','4');
insert into good(id,alias,name,province_id) values (8,'computer','电脑','2');
insert into good(id,alias,name,province_id) values (9,'pack','背','1');
insert into good(id,alias,name,province_id) values (10,'bag','包','5');
insert into good(id,alias,name,province_id) values (11,'screen','屏幕','1');

create table rest(ID INT PRIMARY KEY, NAME VARCHAR(255));

create table csv(ID INT PRIMARY KEY, NAME VARCHAR(255),SEX VARCHAR(255),AGE INT);