alter table private.news
    alter column title type varchar(2550) using title::varchar(2550);

alter table private.news
    alter column url type varchar(2550) using url::varchar(2550);

alter table private.news
    alter column url_to_image type varchar(2550) using url_to_image::varchar(2550);