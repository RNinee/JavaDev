#!/bin/bash

echo "=== DevConnector API Test Script ==="
echo ""

# Colors for output
GREEN='\033[0;32m'
RED='\033[0;31m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

BASE_URL="http://localhost:5001"

echo -e "${BLUE}1. Testing User Login${NC}"
LOGIN_RESPONSE=$(curl -s -X POST "$BASE_URL/api/auth" \
  -H "Content-Type: application/json" \
  -d '{"email":"test@example.com","password":"password123"}')

echo "Response: $LOGIN_RESPONSE"
TOKEN=$(echo $LOGIN_RESPONSE | grep -o '"token":"[^"]*"' | sed 's/"token":"//;s/"//')

if [ -z "$TOKEN" ]; then
    echo -e "${RED}Failed to get token${NC}"
    exit 1
fi

echo -e "${GREEN}✓ Login successful${NC}"
echo "Token: ${TOKEN:0:50}..."
echo ""

echo -e "${BLUE}2. Testing Get Current User${NC}"
curl -s -X GET "$BASE_URL/api/auth" \
  -H "x-auth-token: $TOKEN" | python3 -m json.tool
echo ""

echo -e "${BLUE}3. Testing Create/Update Profile${NC}"
PROFILE_RESPONSE=$(curl -s -X POST "$BASE_URL/api/profile" \
  -H "Content-Type: application/json" \
  -H "x-auth-token: $TOKEN" \
  -d '{
    "status": "Developer",
    "skills": ["JavaScript", "Java", "Spring Boot"],
    "company": "Tech Company",
    "website": "https://example.com",
    "location": "Bangkok, Thailand",
    "bio": "Full-stack developer"
  }')

echo "Response: $PROFILE_RESPONSE" | python3 -m json.tool 2>/dev/null || echo "$PROFILE_RESPONSE"
echo ""

echo -e "${BLUE}4. Testing Get My Profile${NC}"
curl -s -X GET "$BASE_URL/api/profile/me" \
  -H "x-auth-token: $TOKEN" | python3 -m json.tool
echo ""

echo -e "${BLUE}5. Testing Get All Profiles${NC}"
curl -s -X GET "$BASE_URL/api/profile" | python3 -m json.tool
echo ""

echo -e "${GREEN}=== Tests Complete ===${NC}"
