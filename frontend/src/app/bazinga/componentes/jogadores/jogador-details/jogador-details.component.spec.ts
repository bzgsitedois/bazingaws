import { ComponentFixture, TestBed } from '@angular/core/testing';

import { JogadorDetailsComponent } from './jogador-details.component';

describe('JogadorDetailsComponent', () => {
  let component: JogadorDetailsComponent;
  let fixture: ComponentFixture<JogadorDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [JogadorDetailsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(JogadorDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
