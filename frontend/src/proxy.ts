import { NextResponse } from 'next/server';
import type { NextRequest } from 'next/server';

export function proxy(request: NextRequest) {
  const token = request.cookies.get('jwt_token')?.value;

  // Si intentamos ir al dashboard o play sin token, redirigimos al login
  if (!token && (request.nextUrl.pathname.startsWith('/dashboard') || request.nextUrl.pathname.startsWith('/play'))) {
    return NextResponse.redirect(new URL('/login', request.url));
  }

  // Si tenemos token y vamos al login, redirigimos al dashboard
  if (token && request.nextUrl.pathname.startsWith('/login')) {
    return NextResponse.redirect(new URL('/dashboard', request.url));
  }

  return NextResponse.next();
}

export const config = {
  matcher: ['/dashboard/:path*', '/play/:path*', '/login'],
};
