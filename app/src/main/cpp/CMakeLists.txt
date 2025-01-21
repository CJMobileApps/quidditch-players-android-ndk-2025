# Set the minimum CMake version required for this project.
cmake_minimum_required(VERSION 3.22.1)

# Declare the project name
project("quidditchplayersandroid")

## Find JNI and include its directories
#TODO undo to test in CLion
#find_package(JNI REQUIRED)
#include_directories(${JNI_INCLUDE_DIRS})

# Set C++ standard
set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED YES)
set(CMAKE_CXX_EXTENSIONS NO)

# Output the C++ compiler being used
message(STATUS "C++ compiler: ${CMAKE_CXX_COMPILER}")

# Add the shared library target, specifying the source files
add_library(${CMAKE_PROJECT_NAME} SHARED
        src/native-lib.cpp
        src/com/cjmobileapps/quidditchplayers/model/Status.cpp
        src/com/cjmobileapps/quidditchplayers/model/Status.h
)

# Link the necessary libraries to the target
target_link_libraries(${CMAKE_PROJECT_NAME}
        android
        log
)

# Add the executable target (app) which includes both the main source and native-lib
add_executable(app
        main.cpp
        src/native-lib.cpp
        src/com/cjmobileapps/quidditchplayers/model/Status.cpp
        src/com/cjmobileapps/quidditchplayers/model/Status.h
)
