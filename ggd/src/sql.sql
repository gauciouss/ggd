create table area(
	area_id integer primary key,
    area_name varchar(50), 
    parent_id integer    ,
    sort integer
);


insert into area values (1, '臺北市', 0, 1);
insert into area values (100, '中正區', 1, 1);
insert into area values (103, '大同區', 1, 1);
insert into area values (104, '中正區', 1, 1);
insert into area values (105, '松山區', 1, 1);
insert into area values (106, '大安區', 1, 1);
insert into area values (108, '萬華區', 1, 1);
insert into area values (110, '信義區', 1, 1);
insert into area values (111, '士林區', 1, 1);
insert into area values (112, '北投區', 1, 1);
insert into area values (114, '內湖區', 1, 1);
insert into area values (115, '南港區', 1, 1);
insert into area values (116, '文山區', 1, 1);
insert into area values (2, '基隆市', 0, 1);
insert into area values (200, '仁愛區', 2, 1);
insert into area values (201, '信義區', 2, 1);
insert into area values (202, '中正區', 2, 1);
insert into area values (203, '中山區', 2, 1);
insert into area values (204, '安樂區', 2, 1);
insert into area values (205, '暖暖區', 2, 1);
insert into area values (206, '七堵區', 2, 1);
insert into area values (3, '新北市', 0, 1);
insert into area values (207, '萬里區', 3, 1);
insert into area values (208, '金山區', 3, 1);
insert into area values (220, '板橋區', 3, 1);
insert into area values (221, '汐止區', 3, 1);
insert into area values (222, '深坑區', 3, 1);
insert into area values (223, '石碇區', 3, 1);
insert into area values (224, '瑞芳區', 3, 1);
insert into area values (226, '平溪區', 3, 1);
insert into area values (227, '雙溪區', 3, 1);
insert into area values (228, '貢寮區', 3, 1);
insert into area values (231, '新店區', 3, 1);
insert into area values (232, '坪林區 ', 3, 1);
insert into area values (233, '烏來區', 3, 1);
insert into area values (234, '永和區', 3, 1);
insert into area values (235, '中和區', 3, 1);
insert into area values (236, '土城區', 3, 1);
insert into area values (237, '三峽區', 3, 1);
insert into area values (238, '樹林區', 3, 1);
insert into area values (239, '鶯歌區', 3, 1);
insert into area values (241, '三重區', 3, 1);
insert into area values (242, '新莊區', 3, 1);
insert into area values (243, '泰山區', 3, 1);
insert into area values (244, '林口區', 3, 1);
insert into area values (247, '蘆洲區', 3, 1);
insert into area values (248, '五股區', 3, 1);
insert into area values (249, '八里區', 3, 1);
insert into area values (251, '淡水區', 3, 1);
insert into area values (252, '三芝區', 3, 1);
insert into area values (253, '石門區', 3, 1);
insert into area values (4, '南海島', 0, 1);
insert into area values (817, '東沙群島', 4, 1);
insert into area values (819, '南沙群島', 4, 1);
insert into area values (5, '宜蘭縣', 0, 1);
insert into area values (260, '宜蘭市', 5, 1);
insert into area values (261, '頭城鎮', 5, 1);
insert into area values (262, '礁溪鄉', 5, 1);
insert into area values (263, '壯圍鄉', 5, 1);
insert into area values (264, '員山鄉', 5, 1);
insert into area values (265, '羅東鎮', 5, 1);
insert into area values (266, '三星鄉', 5, 1);
insert into area values (267, '大同鄉', 5, 1);
insert into area values (268, '五結鄉', 5, 1);
insert into area values (269, '冬山鄉', 5, 1);
insert into area values (270, '蘇澳鎮', 5, 1);
insert into area values (272, '南澳鄉', 5, 1);
insert into area values (6, '釣魚臺', 0, 1);
insert into area values (290, '釣魚臺', 6, 1);
insert into area values (7, '連江縣', 0, 1);
insert into area values (209, '南竿鄉', 7, 1);
insert into area values (210, '北竿鄉', 7, 1);
insert into area values (211, '莒光鄉', 7, 1);
insert into area values (212, '東引鄉', 7, 1);


insert into area values (8, '新竹市', 0, 1);
insert into area values (9, '新竹縣', 0, 1);
insert into area values (10, '桃園市', 0, 1);
insert into area values (11, '桃園市', 0, 1);
insert into area values (12, '苗栗縣', 0, 1);
insert into area values (13, '臺中市', 0, 1);
insert into area values (14, '彰化縣', 0, 1);
insert into area values (15, '南投縣', 0, 1);
insert into area values (16, '嘉義市', 0, 1);
insert into area values (17, '嘉義縣', 0, 1);
insert into area values (18, '雲林縣', 0, 1);
insert into area values (19, '臺南市', 0, 1);
insert into area values (20, '高雄市', 0, 1);
insert into area values (21, '高雄市', 0, 1);
insert into area values (22, '屏東縣', 0, 1);
insert into area values (23, '澎湖縣', 0, 1);
insert into area values (24, '臺東縣', 0, 1);
insert into area values (25, '高雄市', 0, 1);
insert into area values (26, '金門縣', 0, 1);
insert into area values (27, '花蓮縣', 0, 1);



CREATE TABLE company
(
	company_id VARCHAR(10) NOT NULL,
	name VARCHAR(20),
	area_id INTEGER,
	logo_url VARCHAR(200),
	background_url VARCHAR(200),
	fast_key1 VARCHAR(200),
	fast_key2 VARCHAR(200),
	fast_key3 VARCHAR(200),
	fast_key4 VARCHAR(200),
	isEnabled BIT,
	PRIMARY KEY (company_id),
	KEY (area_id)
) 
;



