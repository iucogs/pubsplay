# --- Created by Slick DDL
# To stop Slick DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table `authors` (`author_id` INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,`lastname` VARCHAR(254) NOT NULL,`firstname` VARCHAR(254) NOT NULL,`Int` INTEGER NOT NULL);
create table `author_of` (`author_id` INTEGER NOT NULL,`citation_id` INTEGER NOT NULL,`position_num` INTEGER NOT NULL);
create table `citations` (`citation_id` INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,`pubtype` VARCHAR(254) NOT NULL,`abstract` VARCHAR(254) NOT NULL,`keywords` VARCHAR(254) NOT NULL,`doi` VARCHAR(254) NOT NULL,`url` VARCHAR(254) NOT NULL,`booktitle` VARCHAR(254) NOT NULL,`chapter` VARCHAR(254) NOT NULL,`edition` VARCHAR(254) NOT NULL,`editor` VARCHAR(254) NOT NULL,`translator` VARCHAR(254) NOT NULL,`journal` VARCHAR(254) NOT NULL,`month` VARCHAR(254) NOT NULL,`number` VARCHAR(254) NOT NULL,`pages` VARCHAR(254) NOT NULL,`publisher` VARCHAR(254) NOT NULL,`location` VARCHAR(254) NOT NULL,`title` VARCHAR(254) NOT NULL,`volume` VARCHAR(254) NOT NULL,`year` VARCHAR(254) NOT NULL);
create table `citations` (`citation_id` INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,`raw` VARCHAR(254) NOT NULL,`owner` VARCHAR(254) NOT NULL);
create table `collections` (`collection_id` INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,`collection_name` VARCHAR(254) NOT NULL,`user_id` INTEGER NOT NULL,`submitter` VARCHAR(254) NOT NULL,`owner` VARCHAR(254) NOT NULL);
create table `member_of_collection` (`collection_id` INTEGER NOT NULL,`citation_id` INTEGER NOT NULL);

# --- !Downs

drop table `authors`;
drop table `author_of`;
drop table `citations`;
drop table `citations`;
drop table `collections`;
drop table `member_of_collection`;

