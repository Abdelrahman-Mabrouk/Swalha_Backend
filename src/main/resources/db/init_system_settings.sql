-- Initialize System Settings
-- This script creates the system_settings table and inserts the default monthly fee

-- Create the system_settings table if it doesn't exist
CREATE TABLE IF NOT EXISTS system_settings (
    id BIGSERIAL PRIMARY KEY,
    setting_key VARCHAR(100) UNIQUE NOT NULL,
    setting_value TEXT NOT NULL,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insert the default monthly fee setting
INSERT INTO system_settings (setting_key, setting_value, description) 
VALUES ('monthly_fee', '70.00', 'Default monthly tuition fee in EGP')
ON CONFLICT (setting_key) DO NOTHING;

-- Verify the setting was created
SELECT * FROM system_settings WHERE setting_key = 'monthly_fee';
