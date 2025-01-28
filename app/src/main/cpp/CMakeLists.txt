# Minimum CMake version
cmake_minimum_required(VERSION 3.22.1)

# Declare the project name
project("quidditchplayersandroid")

# Find JNI and include its directories
find_package(JNI REQUIRED)
include_directories(${JNI_INCLUDE_DIRS})

# Set C++ standard
set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED YES)
set(CMAKE_CXX_EXTENSIONS NO)

# Output the C++ compiler being used
message(STATUS "C++ compiler in parent: ${CMAKE_CXX_COMPILER}")

# Add the shared library target for the main project
add_library(${CMAKE_PROJECT_NAME} SHARED
        src/com/cjmobileapps/quidditchplayers/native-lib.cpp
        src/com/cjmobileapps/quidditchplayers/native-lib.h
)

# Link the necessary libraries to the main target
target_link_libraries(${CMAKE_PROJECT_NAME}
        android
        log
        quidditchplayers-cpp-data
        quidditchplayers-cpp-models
        quidditchplayers-cpp-network # Link the child library
)

# Add an executable target (optional, for testing)
add_executable(app
        main.cpp
        src/com/cjmobileapps/quidditchplayers/native-lib.cpp
        src/com/cjmobileapps/quidditchplayers/native-lib.h
)

# Link the necessary libraries to the app target
target_link_libraries(app
        quidditchplayers-cpp-data
        quidditchplayers-cpp-models # Link the child library
)

# Add the data and model subdirectories separately
add_subdirectory(src/com/cjmobileapps/quidditchplayers/data)
add_subdirectory(src/com/cjmobileapps/quidditchplayers/model)
add_subdirectory(src/com/cjmobileapps/quidditchplayers/network)
