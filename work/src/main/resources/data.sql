INSERT INTO ref_table (allow_ip)
SELECT '119.192.184.137'
WHERE NOT EXISTS (SELECT allow_ip
                  FROM ref_table
                  WHERE allow_ip = '119.192.184.137')
LIMIT 1;
INSERT INTO `user` (active, lgn_id, lgn_pw, lgn_name, `position`)
SELECT 1, 'admin', '$2a$10$9WpKy.CRGD/.6.rz2W1D9eUkvb8LJBGo9BtpZ1of9JuugxTC5//ha', '관리자', 4
WHERE NOT EXISTS (SELECT lgn_id
                  FROM `user`
                  WHERE lgn_id = 'admin')          
LIMIT 1;