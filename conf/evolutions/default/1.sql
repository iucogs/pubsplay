# --- Created by Slick DDL
# To stop Slick DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table `citations` (`citation_id` INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,`pubtype` VARCHAR(254) NOT NULL,`abstract` VARCHAR(254) NOT NULL,`keywords` VARCHAR(254) NOT NULL,`doi` VARCHAR(254) NOT NULL,`url` VARCHAR(254) NOT NULL,`booktitle` VARCHAR(254) NOT NULL,`chapter` VARCHAR(254) NOT NULL,`edition` VARCHAR(254) NOT NULL,`editor` VARCHAR(254) NOT NULL,`translator` VARCHAR(254) NOT NULL,`journal` VARCHAR(254) NOT NULL,`month` VARCHAR(254) NOT NULL,`number` VARCHAR(254) NOT NULL,`pages` VARCHAR(254) NOT NULL,`publisher` VARCHAR(254) NOT NULL,`location` VARCHAR(254) NOT NULL,`title` VARCHAR(254) NOT NULL,`volume` VARCHAR(254) NOT NULL,`year` VARCHAR(254) NOT NULL,`raw` VARCHAR(254) NOT NULL,`owner` VARCHAR(254) NOT NULL);

# --- !Downs

drop table `citations`;

