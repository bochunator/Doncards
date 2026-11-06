INSERT INTO deck (
    name,
    description,
    created_at,
    view_count,
    app_user_id
)
SELECT
    'Greetings!',
    'Welcome to Doncards! This is the official greeting deck. Take a look around and enjoy your stay!',
    CURRENT_TIMESTAMP,
    2147483647,
    id
FROM app_user
WHERE username = 'default words';
