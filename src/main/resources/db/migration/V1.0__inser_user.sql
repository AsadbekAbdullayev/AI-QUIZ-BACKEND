
INSERT INTO public.directions (id, created_at, created_by, modified_at, caption, is_active)
VALUES (1, '2024-04-29 17:14:04.000000', null, '2024-04-29 17:14:08.000000', 'Backend Development', true);

INSERT INTO public.sub_directions (id, created_at, created_by, modified_at, caption, is_active, direction_id)
VALUES (1, '2024-04-29 17:14:42.000000', null, '2024-04-29 17:14:43.000000', 'Java Backend', true, 1);

INSERT INTO public.sub_directions (id, created_at, created_by, modified_at, caption, is_active, direction_id)
VALUES (2, '2024-04-29 17:14:42.000000', null, '2024-04-29 17:14:43.000000', 'Express JS Backend', true, 1);

-- add direction test
INSERT INTO direction_tests (created_at,
                             created_by,
                             modified_at,
                             detailed_test_quantity,
                             standard_test_quantity,
                             sub_direction_id,
                             timer_minutes_for_detailed_test,
                             timer_minutes_for_standard_test)
VALUES (CURRENT_TIMESTAMP, -- Current timestamp for 'created_at'
        null, -- Example UUID for 'created_by'
        CURRENT_TIMESTAMP, -- Current timestamp for 'modified_at'
        10, -- Example quantity for 'detailed_test_quantity'
        20, -- Example quantity for 'standard_test_quantity'
        1, -- Example foreign key ID for 'sub_direction_id'
        60, -- Example minutes for 'timer_minutes_for_detailed_test'
        60 -- Example minutes for 'timer_minutes_for_standard_test'
       );



INSERT INTO users (id,
                   created_at,
                   created_by,
                   modified_at,
                   gender_type,
                   birth_date,
                   display_id,
                   email,
                   email_verification_token,
                   first_name,
                   is_detailed_test_accepted,
                   is_enabled,
                   is_profile_create_test_accepted,
                   last_active,
                   last_name,
                   one_id_pin,
                   password,
                   phone_number,
                   profile_photo_url,
                   region_id,
                   resume_url,
                   sub_direction_id)
VALUES ('123e4567-e89b-12d3-a456-426614174000', -- UUID for user
        current_timestamp, -- Current timestamp
        '123e4567-e89b-12d3-a456-426614174001', -- UUID for creator
        current_timestamp, -- Current timestamp
        'MALE', -- Gender type
        '1990-01-01', -- Birth date
        DEFAULT, -- Serial for display_id
        'example@email.com', -- Email
        'abc123token', -- Email verification token
        'John', -- First name
        TRUE, -- Is detailed test accepted
        TRUE, -- Is enabled
        FALSE, -- Is profile create test accepted
        current_timestamp, -- Last active
        'Doe', -- Last name
        'pin789', -- One ID pin
        '$2a$10$aJx8.jTvDjTBX0sw7l26UuJ39VXtGJvD2vzljpfqi6WVIAzRPYfca', -- Password
        '123-456-7890', -- Phone number
        'http://example.com/photo.jpg', -- Profile photo URL
        1, -- Region ID
        'http://example.com/resume.pdf', -- Resume URL
        1 -- Sub direction ID
       );

