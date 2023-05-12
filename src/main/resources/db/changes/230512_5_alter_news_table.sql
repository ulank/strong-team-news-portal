alter table private.news
    alter column content type varchar(2550) using content::varchar(2550);

alter table private.news
    alter column description type varchar(2550) using description::varchar(2550);