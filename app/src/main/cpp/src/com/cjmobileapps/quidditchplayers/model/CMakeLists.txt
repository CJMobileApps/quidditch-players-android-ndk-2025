# Minimum CMake version
cmake_minimum_required(VERSION 3.22.1)

# Set C++ standard
set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED YES)
set(CMAKE_CXX_EXTENSIONS NO)

# Output the C++ compiler being used
message(STATUS "C++ compiler in model: ${CMAKE_CXX_COMPILER}")

# Define the library target for the model module
add_library(quidditchplayers-cpp-models SHARED
        Status.cpp
        Status.h
        ResponseWrapper.cpp
        ResponseWrapper.h
        Error.cpp
        Error.h
        House.cpp
        House.h
        Positions.cpp
        Positions.h
        Player.cpp
        Player.h
)

# Optionally include directories for this target (if needed by parent)
target_include_directories(quidditchplayers-cpp-models PUBLIC ${CMAKE_CURRENT_SOURCE_DIR})
