INSERT INTO patient (id, first_name, gender, birth_date, email, deleted)
VALUES
    (1, 'Aarav Sharma', 'Male', '1998-04-12', 'aarav.sharma@example.com', false),
    (2, 'Anaya Verma', 'Female', '2000-08-21', 'anaya.verma@example.com', false),
    (3, 'Rohan Mehta', 'Male', '1995-11-03', 'rohan.mehta@example.com', false),
    (4, 'Priya Singh', 'Female', '1999-01-15', 'priya.singh@example.com', false),
    (5, 'Kabir Khan', 'Male', '1997-06-28', 'kabir.khan@example.com', false),
    (6, 'Sanya Gupta', 'Female', '2001-09-10', 'sanya.gupta@example.com', false),
    (7, 'Arjun Kapoor', 'Male', '1994-12-05', 'arjun.kapoor@example.com', false),
    (8, 'Neha Joshi', 'Female', '1996-03-19', 'neha.joshi@example.com', false),
    (9, 'Ishaan Patel', 'Male', '2002-07-25', 'ishaan.patel@example.com', false),
    (10, 'Meera Nair', 'Female', '1993-10-30', 'meera.nair@example.com', false);

INSERT INTO doctor (id, name, specialization, email, deleted)
VALUES
    (1, 'Dr. John Smith', 'Cardiology', 'john.smith@hospital.com', false),
    (2, 'Dr. Sarah Ahmed', 'Neurology', 'sarah.ahmed@hospital.com', false),
    (3, 'Dr. James Wilson', 'Orthopedics', 'james.wilson@hospital.com', false);
