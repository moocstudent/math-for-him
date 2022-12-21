-- 题库表
create table math_question_bank(
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `question` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT "",
    `answer` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT "",
    `type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT "",
    `max_limit` bigint(20) NOT NULL,
    `member_id` varchar(300) NULL, -- memberId 用于区分不同的会员出的题
    `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
PRIMARY KEY (`id`) USING BTREE
)ENGINE = InnoDB AUTO_INCREMENT = 100 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- 会员表
create table math_members(
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `login_name` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT "",
    `login_password` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT "",
    `child_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT "",
    `child_age` int(3) NOT NULL,
    `child_gender` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT "",
    `parent_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT "",
    `parent_email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT "",
    `vip_level` bigint(10) NULL,
    `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
PRIMARY KEY (`id`) USING BTREE
)ENGINE = InnoDB AUTO_INCREMENT = 100 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- 统计会员的答题情况，并结合答题情况展示做题的echarts统计结果
create table member_answer_records(
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `question_id` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT "",
    `wrong_count` bigint(10) NULL DEFAULT 0,
    `right_count` bigint(10) NULL DEFAULT 0,
    `member_id` varchar(300) NOT NULL,
    `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
PRIMARY KEY (`id`) USING BTREE
)ENGINE = InnoDB AUTO_INCREMENT = 100 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;
