


cmake_minimum_required(VERSION 3.4.1)

add_library( key
             SHARED
             key.c )

find_library(log-lib log )

target_link_libraries(key   ${log-lib} )