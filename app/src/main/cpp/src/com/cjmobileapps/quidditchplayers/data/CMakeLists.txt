# Minimum CMake version
cmake_minimum_required(VERSION 3.22.1)

# Set C++ standard
set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED YES)
set(CMAKE_CXX_EXTENSIONS NO)

# Output the C++ compiler being used
message(STATUS "C++ compiler in data: ${CMAKE_CXX_COMPILER}")

# Define the library target for the data module
add_library(quidditchplayers-cpp-data SHARED
        MockData.cpp
        MockData.h
)

# Ensure the data library links to the model library
target_link_libraries(quidditchplayers-cpp-data PUBLIC
        quidditchplayers-cpp-models
        quidditchplayers-cpp-network
)

# Optionally include directories for this target (if needed)
target_include_directories(quidditchplayers-cpp-data PUBLIC ${CMAKE_CURRENT_SOURCE_DIR})
