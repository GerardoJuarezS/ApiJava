package com.miapi.miapi.mapper;


public interface IMapper <I, O>{
    public O map(I in);
}
