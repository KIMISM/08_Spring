# tbl_member를 그냥 삭제하면 외래키 제약조건 때문에 삭제가 안되어 해당 코드 필요
# fk_authorities_users 제약 조건 삭제하는 코드
ALTER TABLE tbl_member_auth DROP FOREIGN KEY  fk_authorities_users;

drop table if exists tbl_member;
create table tbl_member
(
    username varchar (50) primary key,
    password varchar (128) not null,
    email varchar (50) not null,
    reg_date datetime default now(),
    update_date datetime default now()
);

# 사용자 권한 테이블
drop table if exists tbl_member_auth;

create table tbl_member_auth
(
    username varchar (50) not null,
    auth varchar(50) not null,
    primary key(username,auth),
-- tbl_member_auth
constraint fk_authorities_users foreign key (username) references tbl_member (username)
);
insert into tbl_member (username, password, email)
    values
    ('admin', '$2a$10$bkFpEDOqkadxUuHtHJIWKeS23Q727aC7kDZduNrwukKu4smjU8Roi', 'admin@galapgos.org'),
    ('user0', '$2a$10$bkFpEDOqkadxUuHtHJIWKeS23Q727aC7kDZduNrwukKu4smjU8Roi', 'user0@galapgos.org'),
    ('user1', '$2a$10$bkFpEDOqkadxUuHtHJIWKeS23Q727aC7kDZduNrwukKu4smjU8Roi', 'user1@galapgos.org'),
    ('user2', '$2a$10$bkFpEDOqkadxUuHtHJIWKeS23Q727aC7kDZduNrwukKu4smjU8Roi', 'user2@galapgos.org'),
    ('user3', '$2a$10$bkFpEDOqkadxUuHtHJIWKeS23Q727aC7kDZduNrwukKu4smjU8Roi', 'user3@galapgos.org'),
    ('user4', '$2a$10$bkFpEDOqkadxUuHtHJIWKeS23Q727aC7kDZduNrwukKu4smjU8Roi', 'user4@galapgos.org');
select * from tbl_member;

insert into tbl_member_auth (username, auth)
values
('admin','ROLE_ADMIN'),
('admin','ROLE_MANAGER'),
('admin','ROLE_MEMBER'),
('user0','ROLE_MANAGER'),
('user0','ROLE_MEMBER'),
('user1','ROLE_MEMBER'),
('user2','ROLE_MEMBER'),
('user3','ROLE_MEMBER'),
('user4','ROLE_MEMBER');
select * from tbl_member_auth order by auth;

# member 테입르을 기준으로 아우터 조인시켰으므로 권한 정보가 없는 사용자도 조회된다
# username이 'admin'인 사용자의 정보를 권한과 함께 조회
select m.username , password, email, reg_date , update_date , auth
from
    tbl_member m left outer join tbl_member_auth a
        on m.username = a.username
where m.username = 'admin';