-- create database prozacdb;

-- projects <-- n:1 --> projects_resources <-- 1:n --> resources
-- projects <-- 1:n --> tasks <-- 1:n --> hours
use prozacdb;

create table project (
  id bigint(20) unsigned not null auto_increment,
  name varchar(32) not null,
  description varchar(256) not null,
  start_dt timestamp not null,
  end_dt timestamp,
  primary key(id)
);

create table person (
  id bigint(20) unsigned not null auto_increment,
  name varchar(32) not null,
  primary key(id)
);

create table project_person (
  id bigint(20) unsigned not null auto_increment,
  project_id bigint(20),
  person_id bigint(20),
  primary key(id)
);
create unique index ux1_project_person on project_person(project_id, person_id);
alter table project_person 
  add constraint fk_project foreign key(project_id) references project(id);
alter table project_person
  add constraint fk_person foreign key(person_id) references person(id);

create table task (
  id bigint(20) unsigned not null auto_increment,
  project_id bigint(20) not null,
  seq_id varchar(8) not null,
  name varchar(64) not null,
  est_hrs integer not null,
  primary key(id)
);
create unique index ux1_task on task(project_id, seq_id);
alter table task
  add constraint fk_project foreign key(project_id) references project(id);

create table hour (
  id bigint(20) unsigned not null auto_increment,
  task_id bigint(20) not null,
  log_date date not null,
  act_hrs integer not null,
  primary key(id)
);
create unique index ux1_hour on hour(task_id, log_date);
alter table hour 
  add constraint fk_task foreign key(task_id) references task(id);
