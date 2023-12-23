<template>
  <div class="relative bg-white px-6 pb-15 lg:px-8 lg:pb-16 dark:bg-white">
  <!-- ?? -->
  <!-- <div class="absolute inset-0">
    <div class="h-1/3 bg-white sm:h-2/3"></div>
  </div> -->

  <div class="relative mx-auto max-w-7xl">
    <!-- Title -->
        <!-- 이미지를 가운데 정렬하도록 클래스 추가 -->
  <div class="text-center">
    <img style="display: block; margin: 0 auto;" src="/images/editor_choice.png" alt="Random Image">
  </div>

    <!-- 카드 묶음 -->
    <div class="mx-auto mt-12 grid max-w-lg gap-5 lg:max-w-none lg:grid-cols-3">
      <!-- 카드 1-->
      <div class="flex flex-col overflow-hidden shadow-md hover:shadow-orange-300">
        <a href="/">
          <div class="flex-shrink-0">
            <img class="object-cover h-64 w-full" src="/images/error.jpg" alt="">
          </div>
          <div class="flex flex-1 flex-col bg-white p-6">
            <div class="flex-1">
              <div class="flex flex-col justify-center items-center">
                <p class="text-sm font-medium text-gray-400">과학과 자연</p>
                <hr class="gray-300 my-2 w-3/4">
                <p class="text-xl font-semibold text-gray-900">파란 버섯 먹어도 될까?</p>
                <p class="mt-3 text-base text-gray-500">오늘의 주제는 파란 버섯을 먹어도 되는가? 입니다</p>
                <p class="text-base text-gray-500">안녕하세요~ 오늘은 신박한 주제를 들고와봤습니다. 근데 다들 한 번쯤 이런 생각 하시지 않나요? 제가 슈퍼 N이라 그런건가요? ㅎ
                </p>
              </div>
            </div>
            <!-- 해시태그 -->
            <ul class="mt-4 flex flex-wrap items-center justify-start text-sm gap-2">
                  <li title="Pricing type"
                      class="flex items-center cursor-pointer gap-0.5 bg-gray-100 text-black px-2 py-0.5 rounded-full">
                      <span>자연과학</span>
                  </li>
                  <li title="Support for API"
                      class="flex items-center cursor-pointer gap-0.5 bg-gray-100 text-black px-2 py-0.5 rounded-full">
                      <span>음식</span>
                  </li>
              </ul>
            </div>
        </a>
      </div>

      <!-- 카드 2 -->
      <div class="flex flex-col overflow-hidden shadow-md hover:shadow-orange-300">
        <a href="/">
          <div class="flex-shrink-0">
            <img class="object-cover h-64 w-full" src="/images/spring.jpg" alt="">
          </div>
          <div class="flex flex-1 flex-col bg-white p-6">
            <div class="flex-1">
              <div class="flex flex-col justify-center items-center">
                <p class="text-sm font-medium text-gray-400">문화와 예술</p>
                <hr class="gray-300 my-2 w-3/4">
                <p class="text-xl font-semibold text-gray-900">우리 봄이 볼 사람 ^0^~</p>
                <p class="mt-3 text-base text-gray-500">우리 김씨네 자랑 봄이입니다 ㅎ ㅎ 만나는 사람마다 귀엽다고 해요! 어제는 올림픽 공원 갔다왔는데, 봄이가 엄청 좋아했어요! 방방 뛰어다니더라구요 ㅎㅎ 말리느라 고생했어요...</p>
              </div>
            </div>
            <!-- 해시태그 -->
            <ul class="mt-4 flex flex-wrap items-center justify-start text-sm gap-2">
                  <li title="Pricing type"
                      class="flex items-center cursor-pointer gap-0.5 bg-gray-100 text-black px-2 py-0.5 rounded-full">
                      <span>일상</span>
                  </li>
                  <li title="Support for API"
                      class="flex items-center cursor-pointer gap-0.5 bg-gray-100 text-black px-2 py-0.5 rounded-full">
                      <span>강아지</span>
                  </li>
              </ul>
            </div>
        </a>
      </div>

      <!-- 카드 3 -->
      <div class="flex flex-col overflow-hidden shadow-md hover:shadow-orange-300">
        <a href="/">
          <div class="flex-shrink-0">
            <img class="object-cover h-64 w-full" src="/images/slider1.png" alt="">
          </div>
          <div class="flex flex-1 flex-col bg-white p-6">
            <div class="flex-1">
              <div class="flex flex-col justify-center items-center">
                <p class="text-sm font-medium text-gray-400">기술과 개발</p>
                <hr class="gray-300 my-2 w-3/4">
                <p class="text-xl font-semibold text-gray-900">! Tiary 오픈 !</p>
                <p class="mt-3 text-base text-gray-500">Tiary 오픈했어요~~ 많은 사랑 부탁드립니다 ❤️</p>
                <p class="text-base text-gray-500">Tiary는 여러분의 일상을 기록하고 공유할 수 있는 서비스입니다. 여러분의 일상을 기록하고 공유해보세요! 계속 보고 싶은 작가님께는 많은 사랑과 관심을 주세요 😃
                </p>
              </div>
            </div>
            <!-- 해시태그 -->
            <ul class="mt-4 flex flex-wrap items-center justify-start text-sm gap-2">
                  <li title="Pricing type"
                      class="flex items-center cursor-pointer gap-0.5 bg-gray-100 text-black px-2 py-0.5 rounded-full">
                      <span>개발</span>
                  </li>
                  <li title="Support for API"
                      class="flex items-center cursor-pointer gap-0.5 bg-gray-100 text-black px-2 py-0.5 rounded-full">
                      <span>인사</span>
                  </li>
              </ul>
            </div>
        </a>
      </div>

    </div>
  </div>
</div>

</template>