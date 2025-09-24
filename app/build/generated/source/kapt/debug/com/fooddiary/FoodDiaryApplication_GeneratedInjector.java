package com.fooddiary;

import dagger.hilt.InstallIn;
import dagger.hilt.codegen.OriginatingElement;
import dagger.hilt.components.SingletonComponent;
import dagger.hilt.internal.GeneratedEntryPoint;

@OriginatingElement(
    topLevelClass = FoodDiaryApplication.class
)
@GeneratedEntryPoint
@InstallIn(SingletonComponent.class)
public interface FoodDiaryApplication_GeneratedInjector {
  void injectFoodDiaryApplication(FoodDiaryApplication foodDiaryApplication);
}
