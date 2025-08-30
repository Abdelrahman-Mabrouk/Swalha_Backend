-- Fix Revenue Category Constraint
-- This script fixes the check constraint violation for revenue categories

-- First, drop the existing constraint if it exists
ALTER TABLE revenues DROP CONSTRAINT IF EXISTS revenues_category_check;

-- Create a new constraint that allows all the enum values from RevenueCategory
ALTER TABLE revenues ADD CONSTRAINT revenues_category_check 
CHECK (category IN ('Tuition_Fees', 'DONATION', 'BOOKS', 'DOCUMENTS', 'VODAFONE_CASH', 'OTHER'));

-- Verify the constraint was created
SELECT conname, pg_get_constraintdef(oid) 
FROM pg_constraint 
WHERE conrelid = 'revenues'::regclass AND contype = 'c';
