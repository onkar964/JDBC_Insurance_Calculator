create database jdbc_insurance_calculator;
use jdbc_insurance_calculator;
DELIMITER $$

CREATE PROCEDURE get_insurance_multiplier(
    IN dob DATE,
    IN tenure INT,
    IN gender VARCHAR(10),
    OUT multiplier DECIMAL(10,2)
)
BEGIN
    DECLARE age INT;

    SET age = TIMESTAMPDIFF(YEAR, dob, CURDATE());

    IF LOWER(gender) = 'male' THEN
        IF tenure = 2 THEN
            SELECT tenure_2 INTO multiplier FROM male WHERE age = age LIMIT 1;
        ELSEIF tenure = 3 THEN
            SELECT tenure_3 INTO multiplier FROM male WHERE age = age LIMIT 1;
        ELSEIF tenure = 4 THEN
            SELECT tenure_4 INTO multiplier FROM male WHERE age = age LIMIT 1;
        ELSE
            SET multiplier = 0;
        END IF;

    ELSEIF LOWER(gender) = 'female' THEN
        IF tenure = 2 THEN
            SELECT tenure_2 INTO multiplier FROM female WHERE age = age LIMIT 1;
        ELSEIF tenure = 3 THEN
            SELECT tenure_3 INTO multiplier FROM female WHERE age = age LIMIT 1;
        ELSEIF tenure = 4 THEN
            SELECT tenure_4 INTO multiplier FROM female WHERE age = age LIMIT 1;
        ELSE
            SET multiplier = 0;
        END IF;

    ELSE
        SET multiplier = 0;
    END IF;

    IF multiplier IS NULL THEN
        SET multiplier = 0;
    END IF;
END $$

DELIMITER ;
