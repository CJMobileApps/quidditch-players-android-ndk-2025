# Minimum CMake version
cmake_minimum_required(VERSION 3.22.1)

# Set C++ standard
set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED YES)
set(CMAKE_CXX_EXTENSIONS NO)

# Output the C++ compiler being used
message(STATUS "C++ compiler in model: ${CMAKE_CXX_COMPILER}")

# Define the library target for the model module
add_library(quidditchplayers-cpp-network SHARED
        HttpStatus.cpp
        HttpStatus.h
)

# Optionally include directories for this target (if needed by parent)
target_include_directories(quidditchplayers-cpp-network PUBLIC ${CMAKE_CURRENT_SOURCE_DIR})
