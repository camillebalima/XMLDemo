CREATE PROCEDURE `GetCities`(
IN Id_param INT
)
BEGIN
	SELECT * FROM city
    WHERE
    (Id_param IS NULL OR id = Id_param);
END