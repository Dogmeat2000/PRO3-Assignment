-- Run from inside the proper database. In this case 'slaughterhouse_simulation', and not the default 'postgres' database!

-- Create a new user:
CREATE USER slaughterhouseserver WITH PASSWORD 'CF(3''(]eA,JOty$WssTf^3';

-- Grant specific permissions to the user, in the slaughterhouse database schema containing the relevant data:

    -- Grant read permissions:
    GRANT SELECT ON ALL TABLES IN SCHEMA pro3_slaughterhouse TO slaughterhouseserver;

    -- Grant write permissions:
    GRANT INSERT ON ALL TABLES IN SCHEMA pro3_slaughterhouse TO slaughterhouseserver;

    -- Grant modify permissions:
    GRANT UPDATE ON ALL TABLES IN SCHEMA pro3_slaughterhouse TO slaughterhouseserver;

    -- Grant delete permissions:
    GRANT DELETE ON ALL TABLES IN SCHEMA pro3_slaughterhouse TO slaughterhouseserver;

    -- Grant access to the proper schema:
    GRANT USAGE ON SCHEMA pro3_slaughterhouse TO slaughterhouseserver;

    -- Grant access to update sequence numbers, so that data can be added and modified:
    GRANT USAGE, UPDATE, SELECT ON SEQUENCE pro3_slaughterhouse.animal_animal_id_seq TO slaughterhouseserver;
    GRANT USAGE, UPDATE, SELECT ON SEQUENCE pro3_slaughterhouse.animalpart_part_id_seq TO slaughterhouseserver;
    GRANT USAGE, UPDATE, SELECT ON SEQUENCE pro3_slaughterhouse.parttype_id_seq TO slaughterhouseserver;
    GRANT USAGE, UPDATE, SELECT ON SEQUENCE pro3_slaughterhouse.product_product_id_seq TO slaughterhouseserver;
    GRANT USAGE, UPDATE, SELECT ON SEQUENCE pro3_slaughterhouse.tray_tray_id_seq TO slaughterhouseserver;


-- Ensure that this user also has access to future additions or alterations to tables within 'pro3_slaughterhouse' schema:
ALTER DEFAULT PRIVILEGES IN SCHEMA pro3_slaughterhouse
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO slaughterhouseserver;