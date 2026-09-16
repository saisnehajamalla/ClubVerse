USE clubverse;

-- Note: 'password_hash' values are placeholders representing bcrypt hashed passwords
INSERT INTO users (name, email, password_hash, role, batch, department)
VALUES
('Anu', 'anu@iiitkottayam.ac.in', '$2a$12$eImiTXuWVxfM37uY4JANjO5E.8B1eXJqT5.Yh6Y9WpX8RzN1B2c3D', 'STUDENT', '2026', 'CSE'),
('Jannet', 'jannet@iiitkottayam.ac.in', '$2a$12$eImiTXuWVxfM37uY4JANjO5E.8B1eXJqT5.Yh6Y9WpX8RzN1B2c3D', 'CLUB_ADMIN', '2025', 'ECE'),
('Admin', 'admin@iiitkottayam.ac.in', '$2a$12$eImiTXuWVxfM37uY4JANjO5E.8B1eXJqT5.Yh6Y9WpX8RzN1B2c3D', 'COLLEGE_ADMIN', NULL, NULL);

INSERT INTO clubs (club_name, description, created_by)
VALUES
('TinkerHub', 'Innovation Club', 2),
('Music Club', 'Music Activities', 2);

INSERT INTO events (club_id, title, description, venue, event_date, created_by)
VALUES
(1, 'Hackathon', '24 Hour Hackathon', 'Auditorium', '2026-10-20 09:00:00', 2);

INSERT INTO posts (user_id, club_id, content, is_anonymous, moderation_status)
VALUES
(1, 1, 'Looking forward to the hackathon!', TRUE, 'APPROVED');

INSERT INTO notifications (user_id, message)
VALUES
(1, 'Hackathon registrations are now open.');