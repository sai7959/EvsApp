INSERT INTO public.evs_role (active, created_at, deleted, name, updated_at)
SELECT TRUE, CURRENT_TIMESTAMP, FALSE, 'ADMIN', CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM public.evs_role WHERE name = 'ADMIN' and active=true and deleted=false);

INSERT INTO public.evs_role (active, created_at, deleted, name, updated_at)
SELECT TRUE, CURRENT_TIMESTAMP, FALSE, 'VOTER', CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM public.evs_role WHERE name = 'VOTER' and active=true and deleted=false);

INSERT INTO public.evs_role (active, created_at, deleted, name, updated_at)
SELECT TRUE, CURRENT_TIMESTAMP, FALSE, 'ELECTORIAL-OFFICER', CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM public.evs_role WHERE name = 'ELECTORIAL-OFFICER' and active=true and deleted=false);

INSERT INTO public.evs_user (active, created_at, deleted, eligible_to_vote,casted_vote, email, password, updated_at)
SELECT true, CURRENT_TIMESTAMP, false, false,false, 'admin@gmail.com', '$2a$12$J.l8B5/l4OmOP93xibjoN.5r0o363atWPiOfSrD2bU7HtCWfNh/pK', CURRENT_TIMESTAMP
WHERE NOT EXISTS (
    SELECT 1 
    FROM public.evs_user 
    WHERE email = 'admin@gmail.com' AND active = true AND deleted = false
);

INSERT INTO public.evs_user (active, created_at, deleted, eligible_to_vote,casted_vote, email, password, updated_at)
SELECT true, CURRENT_TIMESTAMP, false, false,false, 'electorial-officer@gmail.com', '$2a$12$EiH3XhLHI9HkDK7J2vesseVp30MCwDSHi1WW8FRd1M0GgPD4xAgga', CURRENT_TIMESTAMP
WHERE NOT EXISTS (
    SELECT 1 
    FROM public.evs_user 
    WHERE email = 'electorial-officer@gmail.com' AND active = true AND deleted = false
);


INSERT INTO public.evs_user_role (active, created_at, deleted, updated_at, role_id, user_id)
SELECT true, CURRENT_TIMESTAMP, false, CURRENT_TIMESTAMP, 1, 1
WHERE NOT EXISTS (
    SELECT 1 
    FROM public.evs_user_role 
    WHERE role_id = 1 AND user_id = 1 AND active = true AND deleted = false
); 

INSERT INTO public.evs_user_role (active, created_at, deleted, updated_at, role_id, user_id)
SELECT true, CURRENT_TIMESTAMP, false, CURRENT_TIMESTAMP, 3, 2
WHERE NOT EXISTS (
    SELECT 1 
    FROM public.evs_user_role 
    WHERE role_id = 3 AND user_id = 2 AND active = true AND deleted = false
);




