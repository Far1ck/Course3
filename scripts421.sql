alter table student add constraint age_constraint check (age >= 16);

alter table student add primary key (name);

alter table faculty add constraint name_color_unique_constraint unique (name, color);

alter table student add constraint age_default_constraint default 20 for age;